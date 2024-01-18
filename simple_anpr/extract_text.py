import easyocr
import re
import datetime


# Function to validate
# Indian Vehicle Number Plate
def isValidVehicleNumberPlate(num_plt):
    # Regex to check valid Indian Vehicle Number Plate
    regex = "^[A-Z]{2}[\\s-]{0,1}[0-9]{2}[\\s-]{0,1}[A-Z]{1,2}[\\s-]{0,1}[0-9]{4}$"

    # Compile the ReGex
    p = re.compile(regex)

    # If the string is empty
    # return false
    if num_plt is None:
        return False

    # Return if the string
    # matched the ReGex
    if re.search(p, num_plt):
        return True
    else:
        return False


reader = easyocr.Reader(['en'], gpu=False)  # this needs to run only once to load the model into memory


def ext_txt(img):
    result = reader.readtext(img)
    try:
        if isValidVehicleNumberPlate(result[0][1].replace(" ", "").upper()):

            # print("Validation check :", result[0][1].replace(" ", ""))
            time = str(datetime.datetime.today().replace(microsecond=0))
            json_file = open('name.json', 'w')
            if result[0][1].replace(" ", "") == "KL07CP7235":
                print(result[0][1].replace(" ", ""), "Registered User")
                json_file.write(f'{result[0][1].replace(" ", "")} CAR {time.replace(" ", "::")}')
            else:
                print(result[0][1].replace(" ", ""), "Unregistered User")
            json_file.close()

    except:
        pass


if __name__ == '__main__':
    print(('KL07CP7235'))
