import cv2
import extract_text


# img = cv2.imread('img.jpg')
def find_lic_plt(img):
    lic_data = cv2.CascadeClassifier('haarcascade_russian_plate_number.xml')
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    num = lic_data.detectMultiScale(gray, 1.2)

    for numbers in num:
        (x, y, w, h) = numbers
        roi_color = img[y:y+h, x:x+w]
        cv2.rectangle(img, (x, y), (x+w, y+h), (0, 255, 0), 3)
        extract_text.ext_txt(roi_color)
        # threading.Thread(target=extract_text.ext_txt, args=(roi_color, )).start()

    return img
    # cv2.imshow('Image', roi_color)
    # cv2.waitKey(0)


if __name__ == '__main__':
    cv2.imshow('Image', cv2.imread(r'C:\Users\admin\Desktop\images\vehicle.jpg'))
    cv2.waitKey(0)
    find_lic_plt(cv2.imread(r'C:\Users\admin\Desktop\images\vehicle.jpg'))

