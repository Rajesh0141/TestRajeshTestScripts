package com.crm.VtigerRAJ.CRMTestScript;

import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.crm.VtigerRAJ.ObjectRepository.ContactInformationPage;
import com.crm.VtigerRAJ.ObjectRepository.ContactPage;
import com.crm.VtigerRAJ.ObjectRepository.CreatingNewContactPage;
import com.crm.VtigerRAJ.ObjectRepository.CreatingNewOrganizationPage;
import com.crm.VtigerRAJ.ObjectRepository.HomePage;
import com.crm.VtigerRAJ.ObjectRepository.InformationOfOrganizationPage;
import com.crm.VtigerRAJ.ObjectRepository.OrganizationPage;
import com.crm.VtigerRAJ.ObjectRepository.OrganizationWindowPage;
import com.crm.VtigerRAJ.genericUtility.BaseClass;

@Listeners(com.crm.VtigerRAJ.genericUtility.ListenersImplementationClass.class)
public class CreateContactwithChooseOrganizationTest extends BaseClass {
	
	@Test(groups = {"smoke","IntegrationTest"})
       public void createContactchooseOrganization() throws Throwable, IOException {
        	
      //fetch organization name in excel sheet
    	String expectedOrgName=eLib.getDataFromExcel("CreateCon", 4, 3)+jLib.getrandomNum();
		System.out.println("organization name is "+expectedOrgName);
		
		//Fetching testData from excelSheet
		String industry=eLib.getDataFromExcel("CreateCon", 8, 3);
		
		System.out.println("organization name is "+industry);
		String expectedContactName=eLib.getDataFromExcel("CreateCon", 1, 3);
		System.out.println("organization name is "+expectedContactName);

		//click on organization link
		HomePage homePage=new HomePage(driver);
		homePage.Organization();
	  
		//click on "+" image
		OrganizationPage orgnizationPage=new OrganizationPage(driver);
		orgnizationPage.AddImageIconclick();	
	   //send organizationName and select industry type
		
	    CreatingNewOrganizationPage newOrgnizationname=new CreatingNewOrganizationPage(driver);
	    newOrgnizationname.organizationTextField(expectedOrgName, industry);
	    
		//validation for organizationName
	    InformationOfOrganizationPage infoOrganizationPage=new InformationOfOrganizationPage(driver);
	    String actualOrgname=infoOrganizationPage.actualOrgNameText();
        
	    Assert.assertEquals(actualOrgname.contains(expectedOrgName), true);

		//element will be clickable 
		WebElement contacts = homePage.getContactsLink();
		wLib.waitForElementToBeClickAble(driver,contacts);
		
        //Clcik contactLinkText
		homePage.Contacts(driver);		
		//click "+"Create new contact
		ContactPage contactPage=new ContactPage(driver);
		contactPage.clickAddIcon();
		
		//send the contactLastName and switch the Window
		CreatingNewContactPage createNewContact=new CreatingNewContactPage(driver);
		createNewContact.contactLastName(expectedContactName, actualOrgname, driver);
		
		//choose OrganizationName
		OrganizationWindowPage selectOrgName=new OrganizationWindowPage(driver);
		selectOrgName.chooseOrganizationIn0Window(driver, expectedOrgName);
		
		//switchTo driverWindow ChildWindowTo MainWindow
		createNewContact.contactSave(driver, actualOrgname);
		
		//Verification for Contacts
		ContactInformationPage contactInfoPage=new ContactInformationPage(driver);
		String actualContactName=contactInfoPage.ActualContacxtInfo();
		
		Assert.assertEquals(actualContactName.contains(expectedContactName),true);
		  	
		}	
}















