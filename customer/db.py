# Connect to MySQL database
import json

# Connect to MySQL database
import pymysql

# Replace with your database connection details
connection = pymysql.connect(host='localhost', port=3306, user='root', password='74521CBy', database='51job',
                             charset="utf8")
cursor = connection.cursor()

# Create a Jobs table if not exists
cursor.execute('''
    CREATE TABLE IF NOT EXISTS  details (
    jobId VARCHAR(255),
    jobType VARCHAR(255),
    jobName VARCHAR(255),
    jobTags JSON,
    jobNumString VARCHAR(255),
    workAreaCode VARCHAR(255),
    jobAreaCode VARCHAR(255),
    jobAreaString VARCHAR(255),
    hrefAreaPinYin VARCHAR(255),
    jobAreaLevelDetail JSON,
    provideSalaryString VARCHAR(255),
    issueDateString DATETIME,
    confirmDateString DATETIME,
    workYear VARCHAR(255),
    workYearString VARCHAR(255),
    degreeString VARCHAR(255),
    industryType1 VARCHAR(255),
    industryType2 VARCHAR(255),
    industryType1Str VARCHAR(255),
    industryType2Str VARCHAR(255),
    funcType1Code VARCHAR(255),
    funcType2Code VARCHAR(255),
    major1Str VARCHAR(255),
    major2Str VARCHAR(255),
    encCoId VARCHAR(255),
    companyName VARCHAR(255),
    fullCompanyName VARCHAR(255),
    companyLogo VARCHAR(255),
    companyTypeString VARCHAR(255),
    companySizeString VARCHAR(255),
    companySizeCode VARCHAR(255),
    hrUid VARCHAR(255),
    hrName VARCHAR(255),
    smallHrLogoUrl VARCHAR(255),
    hrPosition VARCHAR(255),
    hrActiveStatusGreen VARCHAR(255),
    hrMedalTitle VARCHAR(255),
    hrMedalLevel VARCHAR(255),
    showHrMedalTitle BOOLEAN,
    hrIsOnline BOOLEAN,
    hrLabels JSON,
    updateDateTime DATETIME,
    lon VARCHAR(255),
    lat VARCHAR(255),
    isCommunicate BOOLEAN,
    isFromXyx BOOLEAN,
    isIntern BOOLEAN,
    isModelEmployer BOOLEAN,
    isQuickFeedback BOOLEAN,
    isPromotion BOOLEAN,
    isApply BOOLEAN,
    isExpire BOOLEAN,
    jobHref VARCHAR(255),
    companyHref VARCHAR(255),
    allowChatOnline BOOLEAN,
    ctmId INT,
    term VARCHAR(255),
    termStr VARCHAR(255),
    landmarkId VARCHAR(255),
    landmarkString VARCHAR(255),
    retrieverName VARCHAR(255),
    exrInfo02 JSON,
    hrInfoType INT,
    jobScheme TEXT,
    coId VARCHAR(255),
    PRIMARY KEY (jobId)
);


''')


def convert_to_json(item):
    for key, value in item.items():
        if isinstance(value, (list, dict)):
            item[key] = pymysql.converters.escape_string(json.dumps(value, ensure_ascii=False))
        elif isinstance(value, dict):
            convert_to_json(value)


# Replace with your actual JSON data
def storage(json_data):
    global keys, values
    data = json.loads(json_data)
    total = data['resultbody']['job']['totalcount']
    # Extracting items from the JSON
    items = data['resultbody']['job']['items']
    # Inserting each item into the table
    for item in items:
        try:
            if 'property' in item.keys():
                del item['property']
            if 'exrInfo02' in item.keys():
                item['exrInfo02'] = json.loads(item['exrInfo02'])
            convert_to_json(item)
            keys = ', '.join(item.keys())
            values = ', '.join(map(lambda x: f'"{x}"' if isinstance(x, str) else str(x), item.values()))
            sql_query = f'INSERT INTO details ({keys}) VALUES ({values});'
            cursor.execute(sql_query)
            print(f"插入一条数据: {item['jobId']}")
        except pymysql.IntegrityError as integrity_err:
            print(f"Integrity Error: {integrity_err}, 执行数据库更新逻辑")
            update_query_template = "UPDATE details SET {update_set} WHERE jobId = %s"
            # Prepare the SET clause for the update query
            update_set_clause = ', '.join([f'{key} = %s' for key in item.keys()])
            # Check the type of each value and format it accordingly
            update_values = [
                f'"{json.dumps(value)}"' if isinstance(value, (list, dict)) else f'"{value}"'
                if isinstance(value, str) else value for value in item.values()
            ]
            # Concatenate the update query string
            update_query = update_query_template.format(update_set=update_set_clause)
            update_query = update_query % (tuple(update_values) + (item['jobId'],))
            # Execute the update query
            cursor.execute(update_query)
    connection.commit()
    return total

# Commit the changes and close the connection
