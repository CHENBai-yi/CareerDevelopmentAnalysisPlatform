import re
import sys
import time

from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains

import db


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


import threading

# 为每个线程创建一个独立的WebDriver实例
thread_local = threading.local()


def a(chrome):
    time.sleep(3)
    try:
        slider = chrome.find_element(value="nc_1_n1z")
        ActionChains(chrome).drag_and_drop_by_offset(slider, 300, 0).perform()
    except Exception as e:
        print("滑动条滑动异常")
        try:
            refresh = chrome.find_element(value="`nc_1_refresh1`")
            refresh2 = chrome.find_element(value="`nc_1_refresh2`")
            refresh.click()
            refresh2.click()
            a(chrome)
        except Exception as e:
            print("`nc_1_refresh1`不存在")
    time.sleep(3)


def click_item(chrome):
    try:
        a(chrome)
        json_data = re.search(r'<pre.*?>(.*?)</pre>', chrome.page_source, re.DOTALL).group(1) if re.search(
            r'<pre.*?>(.*?)</pre>', chrome.page_source, re.DOTALL) else None
        return db.storage(json_data)
    except Exception as e:
        print("不是json数据")
        # click_item(chrome)


def start(chrome):
    chrome.get(
        Config().getRequestUrl(Param.api_key, int(time.time()), Param.keyword, Param.searchType, Param.jobArea,
                               Param.sortType, Param.pageNum, Param.requestId, Param.pageSize, Param.source))
    size = click_item(chrome)
    return size


def init(keyword):
    options = webdriver.ChromeOptions()
    options.add_argument("--disable-blink-features=AutomationControlled")
    chrome = webdriver.Chrome(options=options)
    with open('../static/stealth.min.js') as f:
        js = f.read()
        chrome.execute_cdp_cmd("Page.addScriptToEvaluateOnNewDocument", {
            "source": js
        })
    thread_local.driver = chrome  # 使用适当的浏览器驱动
    Param.keyword = keyword
    total = start(thread_local.driver)
    if total > 0:
        cnt = 0
        if total % Param.pageSize == 0:
            cnt = int(total / Param.pageSize)
        else:
            cnt = int(total / Param.pageSize) + 1
        for i in range(2, cnt + 1):
            Param.pageNum = i
            start(thread_local.driver)
    try:
        thread_local.driver.close()
        db.connection.close()
    except RuntimeError as err:
        db.connection.rollback()
        print(err)


if __name__ == "__main__":
    keyword = []
    threads = []
    try:
        if len(sys.argv[1].split(",")) > 0:
            keyword = sys.argv[1].split(",")
        if len(sys.argv[2]) > 0:
            Param.pageSize = sys.argv[2]
    except Exception as e:
        print(e)
    for key in keyword:
        thread = threading.Thread(target=init, args=(key,), name=key)
        threads.append(thread)
        thread.start()
