import logging
import re
import sys
import threading
import time

from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains

from asyncdb import CustomLogger  # Import the Database class for creating new instances
from asyncdb import Database  # Import the Database class for creating new instances
from asyncdb import thread_local_db  # Import the thread-local Database instance

Logger = CustomLogger(name="asyncApi", log_file="../customer/api.log", log_level=logging.DEBUG,
                      log_format='%(asctime)s %(filename)s %(levelname)s %(message)s', datefmt='%a %d %b %Y %H:%M:%S')


# 入口文件，异步版
class Config:
    url = "https://we.51job.com/pc/search"
    url2 = 'https://we.51job.com/api/job/search-pc?api_key={api_key}&timestamp={timestamp}&keyword={keyword}&searchType={searchType}&pageNum={pageNum}&requestId={requestId}&pageSize={pageSize}&source={source}'

    def getRequestUrl(self, api_key, timestamp, keyword, searchType, jobArea, sortType, pageNum, requestId, pageSize,
                      source):
        return self.url2.format(api_key=api_key, timestamp=timestamp, keyword=keyword, searchType=searchType,
                                jobArea=jobArea, sortType=sortType, pageNum=pageNum, requestId=requestId,
                                pageSize=pageSize, source=source)


class Param:
    sortType = 0
    pageNum = 1
    requestId = 'f155d49fe005e848df7a536820981415'
    pageSize = 300
    source = 1
    api_key = '51job'
    timestamp = int(time.time())
    keyword = 'java开发实习生'
    searchType = 2
    jobArea = 000000


def slide_action(chrome):
    json_data = ""
    time.sleep(RETRY_DELAY)
    try:
        refresh = chrome.find_element(value="`nc_1_refresh1`")
        refresh2 = chrome.find_element(value="`nc_1_refresh2`")
        refresh.click()
        refresh2.click()
        return slide_action(chrome)
    except Exception as e:
        slider = chrome.find_element(value="nc_1_n1z")
        Logger.logger.debug("`nc_1_refresh1`不存在;滑条存在开始滑动滑条")
        # print("`nc_1_refresh1`不存在;滑条存在开始滑动滑条")
        try:
            ActionChains(chrome).drag_and_drop_by_offset(slider, 300, 0).perform()
            time.sleep(RETRY_DELAY)
            json_data = re.search(r'<pre.*?>(.*?)</pre>', chrome.page_source, re.DOTALL).group(1) if re.search(
                r'<pre.*?>(.*?)</pre>', chrome.page_source, re.DOTALL) else None
            if not json_data:
                return slide_action(chrome)
            else:
                return json_data

        except Exception as e:
            # print("滑条不存在")
            Logger.logger.debug("滑条不存在")
            chrome.refresh()
            return slide_action(chrome)


def click_item(chrome):
    json_data = slide_action(chrome)
    total = thread_local_db.db.storage2(json_data)
    return total


MAX_RETRIES = 10  # Maximum number of retries
RETRY_DELAY = 5  # Delay between retries in seconds


def start(chrome):
    chrome.get(Config().getRequestUrl(Param.api_key, int(time.time()), Param.keyword, Param.searchType, Param.jobArea,
                                      Param.sortType, Param.pageNum, Param.requestId, Param.pageSize, Param.source))
    total = click_item(chrome)
    if total > 0:
        cnt = 0
        if total % Param.pageSize == 0:
            cnt = int(total / Param.pageSize)
        else:
            cnt = int(total / Param.pageSize) + 1
        for i in range(2, cnt + 1):
            Param.pageNum = i
            chrome.get(
                Config().getRequestUrl(Param.api_key, int(time.time()), Param.keyword, Param.searchType, Param.jobArea,
                                       Param.sortType, Param.pageNum, Param.requestId, Param.pageSize, Param.source))
            click_item(chrome)
            time.sleep(RETRY_DELAY)


def init(keyword):
    thread_local_db.db = Database()
    options = webdriver.ChromeOptions()
    options.add_argument("--disable-blink-features=AutomationControlled")
    options.add_argument("--headless")
    chrome = webdriver.Chrome(options=options)
    with open('../static/stealth.min.js') as f:
        js = f.read()
        chrome.execute_cdp_cmd("Page.addScriptToEvaluateOnNewDocument", {
            "source": js
        })
    Param.keyword = keyword
    start(chrome)
    try:
        chrome.close()
    except RuntimeError as err:
        Logger.logger.debug("浏览器窗口关闭失败")
        # print()


def main():
    keyword = []
    threads = []
    try:
        if len(sys.argv[1].split(",")) > 0:
            keyword = sys.argv[1].split(",")
        if len(sys.argv[2]) > 0:
            Param.pageSize = sys.argv[2]
    except Exception as e:
        Logger.logger.debug(f'{e},系统入参数量不足，使用默认参数')
        # print(f'{e},系统入参错误')
    for key in keyword:
        thread = threading.Thread(target=init, args=(key,), name=key)
        threads.append(thread)
        thread.start()

    for thread in threads:
        thread.join()


if __name__ == "__main__":
    main()
