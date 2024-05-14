import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import config


class TestCookies(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.get(config.BASE_URL)

    def test_exist_cookies(self):
      username = self.driver.find_element(By.ID, "login")
      username.send_keys("admin")

      password = self.driver.find_element(By.ID, "password")
      password.send_keys("admin")

      login_button = self.driver.find_element(By.ID,"submitLogin")
      login_button.click()

      WebDriverWait(self.driver, 1).until(EC.presence_of_element_located((By.CLASS_NAME, "puimenu-leftBar__logo-img")))
      self.assertIn(f"{config.BASE_URL}/assistiot/home", self.driver.current_url)

      self.driver.execute_script("window.localStorage.clear();")

      self.driver.get(f"{config.BASE_URL}/assistiot/home")
      self.assertIn(f"{config.BASE_URL}/assistiot/login", self.driver.current_url)

    def tearDown(self):
        self.driver.quit()