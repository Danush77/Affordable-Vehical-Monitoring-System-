# using flask_restful
from flask import Flask, jsonify, request
from flask_restful import Resource, Api
import datetime

# creating the flask app
app = Flask(__name__)


@app.route("/get_data")
def get_data():
    json_file = open('name.json', 'r')
    number = json_file.read()
    lic_num = number.split()[0]
    type = number.split()[1]
    time = number.split()[2]
    json_file.close()
    return jsonify({'number plate': lic_num, 'type': type, 'time': time})


# api.add_resource(Square, '/square/<int:num>')

# driver function
if __name__ == '__main__':
    app.run(host="0.0.0.0", debug=True)
