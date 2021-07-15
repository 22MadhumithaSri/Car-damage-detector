import pandas as pd
import numpy as np


import pickle

filename = 'finalized_model.sav'

features_columns = ['YEAR',
 'no_of_damages',
 'score',
 'BMW',
 'Ford',
 300,
 501,
 502,
 503,
 507,
 700,
 1500,
 2002,
 2800,
 '1-Series',
 '1500, 1600, 1800, 1800TI',
 '1500, 1800',
 '1600, 1600-2, 1600TI, 1800, 1800TI, 2000, 2000C, 2000CS, 2000TI',
 '1600, 1600-2, 1800, 1800TI, 2000, 2000C, 2000CS, 2000TI',
 '1600, 1600GT, 1600TI, 1800, 2000, 2000C, 2000CS, 2000TI',
 '1600, 1800, 1800TI, 2000C, 2000CS',
 '1600, 1800, 2000, 2000C, 2000CS',
 '1600, 2000',
 '1600, 2000, 2000 Touring, 2000TII',
 '1941 Ford: Deluxe / Super Deluxe',
 '1949 Ford: Deluxe / Custom',
 '1952 Ford: Mainline / Customline / Crestline',
 '2-Series',
 '2-Series Gran Coupé',
 '2-Series, M2',
 '2000 Touring, 2000TII',
 '2002, 2000TI',
 '2002, 2002TI',
 '2002, 2002TII',
 '250 Isetta',
 '2500, 2800, 2800 Bavaria, 2800CS',
 '2500, 2800, 2800CS',
 '2600, 2600L, 3200CS, 3200L, 3200S',
 '2600, 2600L, 3200L, 3200S',
 '2600L, 3200CS, 3200S',
 '2800CS',
 '3-Series',
 '3-Series Gran Turismo',
 '3-Series, M3',
 '3.0 Bavaria, 3.0CS, 3.0CSL, 3.0S',
 '3.0CS, 3.0CSL, 3.0S',
 '3.0CSI, 3.0S, 3.0SIO',
 '3.0SI',
 '300 Isetta',
 '3200CS',
 '4-Series',
 '4-Series, M4',
 '5-Series',
 '5-Series Gran Turismo',
 '5-Series, M5',
 '6-Series',
 '6-Series Gran Turismo',
 '6-Series, M6',
 '600 Isetta',
 '7-Series',
 '700, 700 LS',
 '8-Series',
 'Aerostar',
 'Aspire',
 'Bronco',
 'Bronco II',
 'C-Max',
 'Contour',
 'Country Squire',
 'Crown Victoria',
 'Custom 500',
 'Durango',
 'E-Series',
 'EXP',
 'EcoSport',
 'Edge',
 'Escape',
 'Escort',
 'Excursion',
 'Expedition',
 'Explorer',
 'Explorer Sport Trac',
 'F-Series',
 'F-Series Super Duty',
 'Fairlane',
 'Fairmont',
 'Falcon',
 'Falcon Ranchero',
 'Festiva',
 'Fiesta',
 'Five Hundred',
 'Flex',
 'Focus',
 'Freestar',
 'Freestyle',
 'Fusion',
 'GT',
 'Galaxie',
 'Gran Torino',
 'Granada',
 'LTD',
 'LTD Crown Victoria',
 'LTD Fox',
 'LTD II',
 'M1',
 'Maverick',
 'Mustang',
 'Mustang Mach-E',
 'Pinto',
 'Probe',
 'Ranchero',
 'Ranger',
 'Taurus',
 'Taurus X',
 'Tempo',
 'Thunderbird',
 'Torino',
 'Transit',
 'Transit Connect',
 'Windstar',
 'X1',
 'X2',
 'X3',
 'X4',
 'X5',
 'X6',
 'X7',
 'Z3',
 'Z3, M',
 'Z4',
 'Z8',
 'i3',
 'i8']

def predict(predict_dict):
    model = pickle.load(open(filename, 'rb'))
    test_df = pd.DataFrame(predict_dict)
    dummy_df = pd.get_dummies(test_df['MAKE'])
    test_df = test_df.merge(dummy_df, left_index=True, right_index=True)
    del test_df['MAKE']
    dummy_df = pd.get_dummies(test_df['MODEL'])
    test_df = test_df.merge(dummy_df, left_index=True, right_index=True)
    del test_df['MODEL']
    del dummy_df
    new_df = pd.DataFrame({}, columns= features_columns)
    new_test_df = test_df.merge(new_df, how='left')
    new_test_df.fillna(0, inplace=True)
    predic_df = pd.DataFrame({}, columns=features_columns)
    predic_df[features_columns] = new_test_df[features_columns]
    predict_data = model.predict(predic_df)
    predicted_price = predict_data[0]
    return predicted_price


#dictionary_to_test = {'MAKE': ['Ford'], 'YEAR': [1996], 'MODEL': ['Aspire'],'no_of_damages':[4],'score':[0.36]}

#value = predict(dictionary_to_test)
#print(value)



