package ru.bellintegrator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class CreateNewUserSeleniumTest {

	@Value("${gecko.driver}")
	private String geckoDriver;

	private WebDriver driver;

	@Before
	public void prepareFirefox() {
		System.setProperty("webdriver.gecko.driver", geckoDriver);
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
	}

	@After
	public void finish() {
		driver.quit();
	}

	/**
	 * Генерация ошибки аутентификации
	 */
	@Test
	public void testBadUsernameLogin() {
		driver.get("http://localhost:8080");
		Assert.assertThat(driver.getTitle(), is("File store"));

		driver.findElement(By.id("upload")).click();

		driver.findElement(By.id("username")).sendKeys(generateAlphabet(4));
		driver.findElement(By.id("password")).sendKeys(generateAlphabet(4));
		driver.findElement(By.id("submit_button")).click();
		String errorMessage = driver.findElement(By.id("spring_security_message")).getText();

		Assert.assertThat(errorMessage.contains("User not found!"), is(true));
	}

	/**
	 * Создание и активация по коду в письме нового пользователя
	 */
	@Test
	public void testUserCreationLogin() {
		String username = generateAlphabet(4);
		String password = generateAlphabet(4);
		String email = getEmailAddress();

		String emailWindowHandle=driver.getWindowHandle();

		createNewTab();

		driver.get("http://localhost:8080");
		Assert.assertThat(driver.getTitle(), is("File store"));

		String storeHandle = driver.getWindowHandle();

		createNewUser(username, password, email);

		driver.switchTo().window(emailWindowHandle);

		clickToActivateUser();

		driver.switchTo().window(storeHandle);
		Assert.assertThat(driver.getTitle(), is("File store"));

		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("submit_button")).click();

		String tagName = driver.findElement(By.id("fileUploadForm")).getText();
		Assert.assertThat(tagName.contains("Choose file"), is(true));
	}

	/**
	 * Создание нового окна для перехода на сайт temp-mail.org
	 */
	private void createNewTab() {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.open()");
		for(String newWindow: driver.getWindowHandles()) {
			driver.switchTo().window(newWindow);
		}
	}

	/** Генерация почтового адреса на сайте temp-mail.org
	 * @return почтовый адрес
	 */
	private String getEmailAddress() {
		try {
			driver.get("https://temp-mail.org/");
		}catch (Exception e) {
			System.out.println("timeout");
		}
		return driver.findElement(By.id("mail")).getAttribute("value");
	}

	/** Последовательность создания нового пользователя
	 * @param username имя пользователя
	 * @param password пароль пользователя
	 * @param email почтовый адрес пользователя
	 */
	private void createNewUser(String username, String password, String email) {
		driver.findElement(By.id("upload")).click();
		driver.findElement(By.id("add_new_user")).click();

		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("submit_button")).click();
	}

	/**
	 * Переход с кодом активации из полученного письма
	 */
	private void clickToActivateUser() {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("asdfg9w3r@yandex.ru")));

		driver.findElement(By.linkText("asdfg9w3r@yandex.ru")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("/activate/")));
		driver.findElement(By.partialLinkText("/activate/")).click();
	}

	/** Генерация строки из случайных символов алфавита
	 * @param size длина строки
	 * @return строка
	 */
	private String generateAlphabet(int size) {
		return generateCode("ABCDEFGHIJKLMNOPQRSTUVWXYZ", size);
	}

	/** Генерация строки из случайных цифровых символов
	 * @param size длина строки
	 * @return строка
	 */
	private String generateDigits(int size) {
		return generateCode("0123456789", size);
	}

	/** Генерация случайной строки из набора символов
	 * @param pattern список символов
	 * @param size длина строки
	 * @return строка
	 */
	private String generateCode(String pattern, int size) {
		StringBuilder result = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			result.append(pattern.charAt(random.nextInt(pattern.length())));
		}
		return result.toString();
	}
}

