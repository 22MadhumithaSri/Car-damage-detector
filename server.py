import flask
from flask import Flask, request

import analyse
from price_prediction import predict

app = Flask(__name__)

@app.route('/')
def hello_admin():
   return 'Hello Admin'

@app.route('/predict',methods=['POST'])
def predicting():
    input = request.json
    if "path" in input.keys():
        imagepath = input["path"]
    response_path = analyse.analyzing(imagepath)
    result = analyse.analyze_results(imagepath)
    print(result)
    dam_objects = result["images"][0]["objects"]["collections"][0]["objects"]
    no_of_objects = len(dam_objects)
    score = 0.0
    for item in dam_objects:
        score = score + float(item["score"])
    score = score / no_of_objects
    newObject = "no_of_damages"
    newObj = no_of_objects
    input[newObject] = newObj
    newScore = "score"
    newVal = float(score)
    input[newScore] = newVal
    price_value = predict(input)
    print(price_value)
    print(score)
    print(no_of_objects)
    return flask.jsonify(response_path=response_path, response_price=price_value)
    #return send_file(response_path, mimetype='image/gif'),response_path


#@app.route('/predict_price',methods=['POST'])
#def price_prediction():
 #   input = request.json
  #  price_value = predict(input)
   # return str(price_value)






if __name__ == '__main__':
    app.run(debug=True)

 # app.run(debug=True, host = '0.0.0.0', port = 7001)
