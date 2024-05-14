import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import config
import time


class TestLogin(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.get(config.BASE_URL)

    def test_login_successful(self):
      username = self.driver.find_element(By.ID, "login")
      username.send_keys("admin")

      password = self.driver.find_element(By.ID, "password")
      password.send_keys("admin")

      login_button = self.driver.find_element(By.ID,"submitLogin")
      login_button.click()

      WebDriverWait(self.driver, 1).until(EC.presence_of_element_located((By.CLASS_NAME, "puimenu-leftBar__logo-img")))

      self.assertIn(f"{config.BASE_URL}/assistiot/home", self.driver.current_url)

      self.driver.delete_all_cookies()

    def test_login_with_wrong_password(self):
      self.driver.delete_all_cookies()
      username = self.driver.find_element(By.ID, "login")
      username.send_keys("admin")

      password = self.driver.find_element(By.ID, "password")
      password.send_keys("12345")

      message_error = self.driver.find_element(By.CLASS_NAME, "pui-login__textError--desktop")
      p_element= message_error.find_element(By.TAG_NAME, "p")
      style = p_element.get_attribute("style")
      self.assertIn("display: none;", style)
      self.assertEqual(p_element.text, "")

      login_button = self.driver.find_element(By.ID,"submitLogin")
      login_button.click()

      # WebDriverWait(self.driver, 1).until(EC.presence_of_element_located((By.CLASS_NAME, "pui-login__textError--desktop")))
      # WebDriverWait(self.driver, 2)
      time.sleep(1)

      p_element = message_error.find_element(By.TAG_NAME, "p")
      style = p_element.get_attribute("style")

      self.assertIsNot("display: none;", style)
      self.assertNotEqual(p_element.text, "")
      self.assertIn(f"{config.BASE_URL}/assistiot/login", self.driver.current_url)
      self.driver.delete_all_cookies()

    def tearDown(self):
        self.driver.quit()


