import os
import json
from ibm_watson import VisualRecognitionV4
from ibm_cloud_sdk_core.authenticators import IAMAuthenticator
from ibm_watson.visual_recognition_v4 import AnalyzeEnums, FileWithMetadata
import draw_json

authenticator = IAMAuthenticator('4-ejwb43zCRCOJAgo1p-l_arPHzyNG3wWihdPWzdIqjm')
visual_recognition = VisualRecognitionV4(
    version='2019-02-11',
    authenticator=authenticator
)
visual_recognition.set_service_url("https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/63946c59-5e29-4323-99f1-6c1b114f4e95")

collect_id = "e05d041b-3181-484a-aaee-17785cf11bc2"

def analyze_results( path):
    try:
        with open(path, 'rb') as honda_file:
            result = visual_recognition.analyze(
                collection_ids=[collect_id],
                features=[AnalyzeEnums.Features.OBJECTS.value],
                images_file=[
                    FileWithMetadata(honda_file)
                ], threshold=0.25).get_result()
            print(result)
            print(type(result))
            return result
    except:
        print(path)
        return None
def analyzing(path):
    result = analyze_results(path)
    if result is not None:
        image_path = draw_json.drawing_json(path,result)
        return image_path