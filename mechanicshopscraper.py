from flask import Flask,jsonify,request
from bs4 import BeautifulSoup
import selenium
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import json
import os
import time
def getReviewstwo(zone):
   
    return shops
app = Flask(__name__)
@app.route("/damagedetails/<string:zone>")
def get(zone):
    assert zone==request.view_args['zone']
    options = Options()
    options.add_argument("--headless")
    options.add_argument("--disable-gpu")
    options.add_argument("--no-sandbox")
    options.add_argument("enable-automation")
    options.add_argument("--disable-infobars")
    options.add_argument("--disable-dev-shm-usage")
    driver = webdriver.Chrome(options=options)
    search = "car service center {}".format(zone) 
    url =f"https://www.google.com/search?q={search}"
    names,addresses,phones,userstars,openstatuses=[],[],[],[],[]
    driver.get(url)
    wait = WebDriverWait(driver, 10) 
    menu_bt = wait.until(EC.element_to_be_clickable( (By.XPATH, '//div[@class="hdtb-mitem hdtb-imb"]//a')) )
    menu_bt.click()
    response = BeautifulSoup(driver.page_source, 'html.parser')
    rlist = response.find_all('div', class_='section-result-content')
    print(rlist)
    name=driver.find_elements_by_xpath('//h3[@class="section-result-title"]//span')  
    address=driver.find_elements_by_xpath('//div[@class="section-result-details-container"]//span[@class="section-result-location"]')
    phone=driver.find_elements_by_xpath('//div[@class="section-result-hours-phone-container"]//span[@class="section-result-info section-result-phone-number"]//span[1]')
    userstar=driver.find_elements_by_xpath('//div[@class="section-result-text-content"]//span[@class="cards-rating-score"]')
    openstatus=driver.find_elements_by_xpath('//div[@class="section-result-hours-phone-container"]//span[@class="section-result-info section-result-closed" and not(contains(@style, "display:none"))]//span[1] | //div[@class="section-result-hours-phone-container"]//span[@class="section-result-info section-result-opening-hours" and not(contains(@style, "display:none"))]//span[1]')
    for e in address:
        print(e.text)
        addresses.append(e.text)    
    for f in name:
        print("name")
        print(f.text)
        names.append(f.text)
    for g in phone:
        print(g.text)
        phones.append(g.text)
    for q in openstatus:
        if q.text==".":
        	openstatuses.append(" ")  
        else:   
         openstatuses.append(q.text)
    for r in userstar :
        userstars.append(r.text) 
    print(phones)
    print(names)
    print(addresses)
    print(openstatuses)
    print(userstars)   
    score_titles = [{"name": t, "address": s,"phone":u,"rating":v,"status":w} for t, s, u, v, w in zip(names,addresses,phones,userstars,openstatuses)] 
    shops=json.dumps(score_titles)

    driver.quit()
    return jsonify({"area":score_titles})


if __name__== "__main__":
    app.run(host='0.0.0.0',port=8080)