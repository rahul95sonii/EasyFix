package com.easyfix.easyfixers.action;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.easyfix.Jobs.delegate.JobService;
import com.easyfix.Jobs.model.Jobs;
import com.easyfix.clients.delegate.ClientService;
import com.easyfix.clients.model.Clients;
import com.easyfix.easyfixers.delegate.EasyfixerService;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.easyfixers.model.ServicePayout;
import com.easyfix.settings.delegate.CityService;
import com.easyfix.settings.delegate.DocumentTypeService;
import com.easyfix.settings.delegate.ServiceTypeService;
import com.easyfix.settings.delegate.SkillService;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.DocumentType;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.settings.model.Skill;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.CommonMasterData;
import com.easyfix.util.Constants;
import com.easyfix.util.RestClient;
import com.easyfix.util.TargetURISingleton;
import com.easyfix.util.UtilityFunctions;
import com.easyfix.user.delegate.UserService;
import com.easyfix.user.model.User;
import com.opensymphony.xwork2.ModelDriven;

public class EasyfixerAction extends CommonAbstractAction implements ModelDriven<Easyfixers>
{

	private static final Logger logger = LogManager.getLogger(EasyfixerAction.class);
	private static final long serialVersionUID = 1L;

	// private String uploadPathFix ;
	private EasyfixerService easyfixerService;
	private CityService cityService;
	private SkillService skillService;
	private ServiceTypeService serviceTypeService;
	private DocumentTypeService documentTypeService;
	private ClientService clientService;
	private UserService userService;
	private JobService jobService;
	private User userObj;
	private CommonMasterData commonMasterData ;
	private Easyfixers easyfixerObj;
	private String actMenu;
	private String actParent;
	private String title;
	private String redirectUrl;
	
	private ServicePayout payoutObj;

	private List<User> NDMList;
	private List<Easyfixers> inactiveReasonList;

	private List<Easyfixers> easyfixerList;
	private List<City> cityList;
	private List<Skill> skillList;
	private List<ServiceType> serviceTypeList;
	private List<DocumentType> documentTypeList;
	// private String uploadPath = "O:\\ef_documents\\";
	private String uploadPath = "/var/www/html/easydoc/easyfixer_documents/";
	private String fileName;
	FileInputStream fileInputStream;
	 
	// private String uploadPath;
	@Override
	public Easyfixers getModel()
	{
		return setEasyfixerObj(new Easyfixers());
	}

	public String easyfixer() throws Exception
	{

		try
		{

			String acttitle = "Easyfix : Manage EasyFixer";
			setActMenu("Manage EasyFixers");
			setActParent("EasyFixers");
			setTitle(acttitle);

			easyfixerList = easyfixerService.getEasyfixerList(2); // 2 for all
																	// easyfixer
			// uploadPath =getText("path.uploadpath");

			// System.out.println(getUploadPathFix()+":: "+uploadPathFix);
			// System.out.println(getText("path.uploadpath"));
			// System.out.println(uploadPath);

		}

		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);
		}

		return SUCCESS;

	}

	public String addEditEasyfixer() throws Exception
	{

		try
		{
			
			cityList = cityService.getCityList();
			serviceTypeList = serviceTypeService.getSerTypeList(1);
			NDMList = userService.userListByRole("12,13"); // 12,13 for NDM
			skillList = skillService.getSkillList();

			if (easyfixerObj.getEasyfixerId() != 0)
			{
				easyfixerObj = easyfixerService.getEasyfixerDetailsById(easyfixerObj.getEasyfixerId());
				documentTypeList = easyfixerService.getEasyfixerDocument(easyfixerObj.getEasyfixerId());
				inactiveReasonList = easyfixerService.getEnumReasonList(4); // 1
																			// for
																			// cancel
																			// reason
			}
			else
			{
				documentTypeList = easyfixerService.getEasyfixerDocument(0);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);
		}

		return SUCCESS;
	}

public String efrPayoutSheet() throws Exception{
	
		return SUCCESS;
		
	}
	public String downloadEfrPayoutSheet() throws Exception{

		fileName = "efrPayout.xlsx";
		try{
			int flag = easyfixerObj.getReportFlag();
			
				
			
			String range = easyfixerObj.getDateRange();
			String[] temp = range.split("--");
			String start = temp[0].trim();
			String end = temp[1].trim();

			String startDate = UtilityFunctions.convertStringToDate(start);
			String endDate = UtilityFunctions.convertStringToDate(end);
			Date d = UtilityFunctions.convertStringToDateInDate(endDate, "yyyy-MM-dd");
			endDate = UtilityFunctions.addSubstractDaysInDate(d, 1, "yyyy-MM-dd");
			//falg =100 all, client =0 all
		//	fileInputStream= completedJobsReportService.downloadCompletedJobReport("2015-07-01","2015-12-10",0,1);
		
			fileInputStream = easyfixerService.downloadEfrPayoutSheet(startDate, endDate, flag);
		
		}
		catch(Exception e){
			e.printStackTrace();
			/*
			 * <option value="100">All</option>
													<option value="0">New Created</option>
													<option value="1">Scheduled</option>
													<option value="2">CheckedIn</option>
													<option value="3">CheckedOut(Revenue)</option>
													<option value="5">Completed</option>
													<option value="6">Canceled</option>
													<option value="7">Enquiry</option>
													<option value="9">Call Later</option>
													<option value="50">Re Schedule</option>
			 */
		}
		return SUCCESS;
	
		
	}
	public String addUpdateEasyFixer() throws Exception
	{

		try
		{
			int userId = ((User) requestObject.getSession().getAttribute(Constants.USER_OBJECT)).getUserId();
			easyfixerObj.setUpdatedBy(userId);

			if (easyfixerObj.getEasyfixerDocumentName0() != null && !easyfixerObj.getEasyfixerDocumentName0().isEmpty())
			{
				String[] temp = easyfixerObj.getEasyfixerDocumentName0().split("\\.");
				String sysGeneratedFileName = "EFRDoc" + new Date().getTime() + "." + temp[1];
				String fullFileName = uploadPath + sysGeneratedFileName;

				File theFile = new File(fullFileName);
				FileUtils.copyFile(easyfixerObj.getEasyfixerDocFile0(), theFile);
				easyfixerObj.setEasyfixerDocumentName0(sysGeneratedFileName);

			}
			else
			{
				easyfixerObj.setEasyfixerDocumentName0(easyfixerObj.getEasyfixerPrevDocName0());
			}
			if (easyfixerObj.getEasyfixerDocumentName1() != null && !easyfixerObj.getEasyfixerDocumentName1().isEmpty())
			{
				String[] temp1 = easyfixerObj.getEasyfixerDocumentName1().split("\\.");
				String sysGeneratedFileName1 = "EFRDoc" + new Date().getTime() + "." + temp1[1];
				String fullFileName1 = uploadPath + sysGeneratedFileName1;

				File theFile = new File(fullFileName1);
				FileUtils.copyFile(easyfixerObj.getEasyfixerDocFile1(), theFile);
				easyfixerObj.setEasyfixerDocumentName1(sysGeneratedFileName1);
			}
			else
			{
				easyfixerObj.setEasyfixerDocumentName1(easyfixerObj.getEasyfixerPrevDocName1());
			}
			if (easyfixerObj.getEasyfixerDocumentName2() != null && !easyfixerObj.getEasyfixerDocumentName2().isEmpty())
			{
				String[] temp2 = easyfixerObj.getEasyfixerDocumentName2().split("\\.");
				String sysGeneratedFileName2 = "EFRDoc" + new Date().getTime() + "." + temp2[1];
				String fullFileName2 = uploadPath + sysGeneratedFileName2;

				File theFile = new File(fullFileName2);
				FileUtils.copyFile(easyfixerObj.getEasyfixerDocFile2(), theFile);
				easyfixerObj.setEasyfixerDocumentName2(sysGeneratedFileName2);
			}
			else
			{
				easyfixerObj.setEasyfixerDocumentName2(easyfixerObj.getEasyfixerPrevDocName2());
			}
			if (easyfixerObj.getEasyfixerDocumentName3() != null && !easyfixerObj.getEasyfixerDocumentName3().isEmpty())
			{
				String[] temp3 = easyfixerObj.getEasyfixerDocumentName3().split("\\.");
				String sysGeneratedFileName3 = "EFRDoc" + new Date().getTime() + "." + temp3[1];
				String fullFileName3 = uploadPath + sysGeneratedFileName3;

				File theFile = new File(fullFileName3);
				FileUtils.copyFile(easyfixerObj.getEasyfixerDocFile3(), theFile);
				easyfixerObj.setEasyfixerDocumentName3(sysGeneratedFileName3);
			}
			else
			{
				easyfixerObj.setEasyfixerDocumentName3(easyfixerObj.getEasyfixerPrevDocName3());
			}
			if (easyfixerObj.getEasyfixerDocumentName4() != null && !easyfixerObj.getEasyfixerDocumentName4().isEmpty())
			{
				String[] temp4 = easyfixerObj.getEasyfixerDocumentName4().split("\\.");
				String sysGeneratedFileName4 = "EFRDoc" + new Date().getTime() + "." + temp4[1];
				String fullFileName4 = uploadPath + sysGeneratedFileName4;

				File theFile = new File(fullFileName4);
				FileUtils.copyFile(easyfixerObj.getEasyfixerDocFile4(), theFile);
				easyfixerObj.setEasyfixerDocumentName4(sysGeneratedFileName4);
			}
			else
			{
				easyfixerObj.setEasyfixerDocumentName4(easyfixerObj.getEasyfixerPrevDocName4());
			}
			if (easyfixerObj.getEasyfixerDocumentName5() != null && !easyfixerObj.getEasyfixerDocumentName5().isEmpty())
			{
				String[] temp5 = easyfixerObj.getEasyfixerDocumentName5().split("\\.");
				String sysGeneratedFileName5 = "EFRDoc" + new Date().getTime() + "." + temp5[1];
				String fullFileName5 = uploadPath + sysGeneratedFileName5;

				File theFile = new File(fullFileName5);
				FileUtils.copyFile(easyfixerObj.getEasyfixerDocFile5(), theFile);
				easyfixerObj.setEasyfixerDocumentName5(sysGeneratedFileName5);
			}
			else
			{
				easyfixerObj.setEasyfixerDocumentName5(easyfixerObj.getEasyfixerPrevDocName5());
			}

			int stauts = easyfixerService.addUpdateEasyFixer(easyfixerObj);

			// remap easyfixer to all the mapped client if irrespective of
			// service types are added to Easyfixer or not
			List<Clients> mappedClient = easyfixerService.getClientListForEasyfixer(easyfixerObj.getEasyfixerId(), 1);

			String EfrServiceType = easyfixerObj.getEasyfixerServiceType();// easyfixerServiceType
			// String flag = requestObject.getParameter("flag");
			List<String> list = new ArrayList<String>(Arrays.asList(EfrServiceType.split(",")));

			for (Clients client : mappedClient)
			{
				// System.out.println(client.getCityId());
				int status = clientService.updateMappedEasyfixer(client.getClientId(), easyfixerObj.getEasyfixerId(), "Map", list);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);
		}

		return SUCCESS;
	}

	public String manageEasyfixerClientMapping() throws Exception
	{

		try
		{

			String acttitle = "Easyfix : Manage EasyFixer";
			setActMenu("Manage EasyFixers");
			setActParent("EasyFixers");
			setTitle(acttitle);

			easyfixerObj = easyfixerService.getEasyfixerDetailsById(easyfixerObj.getEasyfixerId());
			List<Clients> clientListforEasyfixer = easyfixerService.getClientListForEasyfixer(easyfixerObj.getEasyfixerId(), 2); // 2
			
			requestObject.setAttribute("clientListforEasyfixer", clientListforEasyfixer);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);

		}

		return SUCCESS;
	}

	public String addUpdateClientMapping() throws Exception
	{
		try
		{

			int clientId = Integer.parseInt(requestObject.getParameter("clientId"));
			String c = requestObject.getParameter("serviceTypeIds");
			String flag = requestObject.getParameter("flag");
			List<String> list = new ArrayList<String>(Arrays.asList(c.split(",")));

			int status = clientService.updateMappedEasyfixer(clientId, easyfixerObj.getEasyfixerId(), flag, list);

			requestObject.setAttribute("status", status);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;

	}

	public String checkBalance()
	{

		try
		{
			setTitle("Easyfix : Easyfixer Search");
			setActMenu("Search");
			setActParent("EasyFixers");
			serviceTypeList = serviceTypeService.getSerTypeList(1);
			NDMList = userService.userListByRole("12,13"); // 12,13 for NDM

		}
		catch (Exception e)
		{
			logger.catching(e);
		}

		return SUCCESS;
	}

	public String checkBalanceDetails()
	{
		try
		{

			easyfixerList = easyfixerService.easyfixerBalance(easyfixerObj);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	public String servicemenPayoutMapping() throws Exception
	{
		try
		{
			String acttitle = "Easyfix : Servicemen Payout";
			setActMenu("Servicemen Payout");
			setActParent("EasyFixers");
			setTitle(acttitle);
			commonMasterData= new CommonMasterData();
			cityList= commonMasterData.cityList();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);

		}

		return SUCCESS;
	}

	
	public String getServicemenPayoutList() throws Exception
	{
		try
		{
			int cityId = Integer.parseInt(requestObject.getParameter("cityId"));
			easyfixerList= easyfixerService.getEasyfixerListForPayout(cityId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);

		}

		return SUCCESS;
	}
	
	
	public String saveServicePayout() throws Exception
	{
		int userId = ((User) requestObject.getSession().getAttribute(Constants.USER_OBJECT)).getUserId();
		try {
			payoutObj = new ServicePayout();			
			payoutObj.setEfrId(Integer.parseInt(requestObject.getParameter("efrId")));
			payoutObj.setEfrBalance(Float.parseFloat(requestObject.getParameter("balance")));
			payoutObj.setOpsAmount(Float.parseFloat(requestObject.getParameter("payout")));
			payoutObj.setOpsApprovedAmount(Float.parseFloat(requestObject.getParameter("val")));
			payoutObj.setPayoutId(Integer.parseInt(requestObject.getParameter("payoutId")));
			int res = easyfixerService.saveServicePayOut(payoutObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String finApprovePayout() {
		int userId = ((User) requestObject.getSession().getAttribute(Constants.USER_OBJECT)).getUserId();
		try {
			int efrId = Integer.parseInt(requestObject.getParameter("efrId"));
			int payoutId = Integer.parseInt(requestObject.getParameter("payoutId"));
			float finAprvAmnt = Float.parseFloat(requestObject.getParameter("finAprvAmnt"));
			int status = Integer.parseInt(requestObject.getParameter("status"));
			
			int res = easyfixerService.approvePayOutByFinance(payoutId,efrId,userId,finAprvAmnt,status);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	public String resetEfrAppPassword(){
		int userId = ((User) requestObject.getSession().getAttribute(Constants.USER_OBJECT)).getUserId();
		int efrId = easyfixerObj.getEasyfixerId();
		try {
			String newPassword = UtilityFunctions.generateRandomNo(6);
			URIBuilder uribuilder = TargetURISingleton.getUriForHttpClient()
					.setPath("/v1/easyfixers")
					.addParameter("action", "resetPassword")
					.addParameter("id", efrId+"")
					.addParameter("newPassword", newPassword);
			URI uri = uribuilder.build();
			System.out.println(uri);
			
			HttpPatch patchRequest = new HttpPatch(uri);
			
			patchRequest.addHeader("Content-Type","application/json");
		//	patchRequest.addHeader("clientId",jObj.getFkClientId()+"");
		//	patchRequest.addHeader("Accept","application/json");
			CredentialsProvider credentialsProvider= TargetURISingleton.getCredentialProviderForCRM();
	//		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("easyfixcrm", "09dce46b-5b88-424f-a401-b20390aab6d5"));
			HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
		//	HttpClient client = TargetURISingleton.getHttpClient();
			HttpResponse response =client.execute(patchRequest);
			if(response.getStatusLine().getStatusCode()==204){
				requestObject.setAttribute("msg", newPassword);
				logger.info("password reset successful for efr:"+efrId +"new Password:"+newPassword);
				Easyfixers efr = easyfixerService.getEasyfixerDetailsById(efrId);
				efr.setAppLoginPassword(newPassword);
				Jobs j = new Jobs();
				j.setEasyfixterObj(efr);
				jobService.sendSms(0, "efrPasswordReset", 3, 4, efr.getEasyfixerNo(),j);
			}
			else{
				logger.info("failes:: password reset for efr:"+efrId);
				requestObject.setAttribute("msg", "password reset failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	
	public EasyfixerService getEasyfixerService()
	{
		return easyfixerService;
	}

	public void setEasyfixerService(EasyfixerService easyfixerService)
	{
		this.easyfixerService = easyfixerService;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getRedirectUrl()
	{
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl)
	{
		this.redirectUrl = redirectUrl;
	}

	public List<Easyfixers> getEasyfixerList()
	{
		return easyfixerList;
	}

	public void setEasyfixerList(List<Easyfixers> easyfixerList)
	{
		this.easyfixerList = easyfixerList;
	}

	public Easyfixers getEasyfixerObj()
	{
		return easyfixerObj;
	}

	public Easyfixers setEasyfixerObj(Easyfixers easyfixerObj)
	{
		this.easyfixerObj = easyfixerObj;
		return easyfixerObj;
	}

	public CityService getCityService()
	{
		return cityService;
	}

	public void setCityService(CityService cityService)
	{
		this.cityService = cityService;
	}

	public ServiceTypeService getServiceTypeService()
	{
		return serviceTypeService;
	}

	public void setServiceTypeService(ServiceTypeService serviceTypeService)
	{
		this.serviceTypeService = serviceTypeService;
	}

	public List<City> getCityList()
	{
		return cityList;
	}

	public void setCityList(List<City> cityList)
	{
		this.cityList = cityList;
	}

	public List<ServiceType> getServiceTypeList()
	{
		return serviceTypeList;
	}

	public void setServiceTypeList(List<ServiceType> serviceTypeList)
	{
		this.serviceTypeList = serviceTypeList;
	}

	public List<DocumentType> getDocumentTypeList()
	{
		return documentTypeList;
	}

	public void setDocumentTypeList(List<DocumentType> documentTypeList)
	{
		this.documentTypeList = documentTypeList;
	}

	public DocumentTypeService getDocumentTypeService()
	{
		return documentTypeService;
	}

	public void setDocumentTypeService(DocumentTypeService documentTypeService)
	{
		this.documentTypeService = documentTypeService;
	}

	/*
	 * public String getUploadPathFix() { return uploadPathFix; }
	 * 
	 * public void setUploadPathFix(String uploadPathFix) { this.uploadPathFix =
	 * uploadPathFix; }
	 */

	public String getActMenu()
	{
		return actMenu;
	}

	public void setActMenu(String actMenu)
	{
		this.actMenu = actMenu;
	}

	public String getActParent()
	{
		return actParent;
	}

	public void setActParent(String actParent)
	{
		this.actParent = actParent;
	}

	@Override
	// for RestrictAccessToUnauthorizedActionInterceptor
	public String toString()
	{
		return "EasyfixerAction";
	}

	public ClientService getClientService()
	{
		return clientService;
	}

	public void setClientService(ClientService clientService)
	{
		this.clientService = clientService;
	}

	public List<Easyfixers> getInactiveReasonList()
	{
		return inactiveReasonList;
	}

	public void setInactiveReasonList(List<Easyfixers> inactiveReasonList)
	{
		this.inactiveReasonList = inactiveReasonList;
	}

	public List<User> getNDMList()
	{
		return NDMList;
	}

	public void setNDMList(List<User> nDMList)
	{
		NDMList = nDMList;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public User getUserObj()
	{
		return userObj;
	}

	public void setUserObj(User userObj)
	{
		this.userObj = userObj;
	}

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	public ServicePayout getPayoutObj() {
		return payoutObj;
	}

	public void setPayoutObj(ServicePayout payoutObj) {
		this.payoutObj = payoutObj;
	}

	public JobService getJobService() {
		return jobService;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FileInputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public SkillService getSkillService() {
		return skillService;
	}

	public void setSkillService(SkillService skillService) {
		this.skillService = skillService;
	}

	public List<Skill> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<Skill> skillList) {
		this.skillList = skillList;
	}

	

}
