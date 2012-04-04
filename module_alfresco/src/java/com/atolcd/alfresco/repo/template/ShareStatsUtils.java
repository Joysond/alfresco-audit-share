package com.atolcd.alfresco.repo.template;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.template.BaseTemplateProcessorExtension;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PersonService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class ShareStatsUtils extends BaseTemplateProcessorExtension implements InitializingBean {
	private NodeService nodeService;
	private PersonService personService;

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(nodeService, "There must be a NodeService");
		Assert.notNull(personService, "There must be a PersonService");
	}

	public String getPersonFullName(String username) {
		if (this.personService.personExists(username)) {
			NodeRef person = this.personService.getPerson(username);

			String firstName = (String) this.nodeService.getProperty(person, ContentModel.PROP_FIRSTNAME);
			String lastName = (String) this.nodeService.getProperty(person, ContentModel.PROP_LASTNAME);

			return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName.toUpperCase() : "");
		}

		return "";
	}
}