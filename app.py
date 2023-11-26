import json

from flask import Flask, Response

app = Flask(__name__)


@app.route('/')
def hello_world():  # put application's code here
    return 'Hello World!'


@app.route(rule="/api/v/<job>/", methods=['GET'])
def hello_worlds(job):  # put application's code here
    print(job)
    data = {
        "msg": "测试",
        "data": ["张三", "李四", "王五"]
    }
    return Response(mimetype="application/json", status=200, response=json.dumps(data))


if __name__ == '__main__':
    app.config["JSON_AS_ASCII"] = False
    app.run(port=8081, debug=True)
