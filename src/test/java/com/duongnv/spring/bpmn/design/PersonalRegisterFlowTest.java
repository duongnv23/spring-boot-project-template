package com.duongnv.spring.bpmn.design;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.duongnv.spring.web.flow.register.PersonalRegisterForm;

import javassist.compiler.ast.Variable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonalRegisterFlowTest {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	// private CustomerService customerService;
	private ApplicationContext applicationContext;

	// @Test
	public void testDeploy() {
		repositoryService.createDeployment().addClasspathResource("processes/PersonalRegister.bpmn").deploy();
		System.out
				.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
	}

	@Test
	public void testStartProcess() {

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("customerName", "Duong");
		variables.put("phoneNumber", "0979586233");
		variables.put("token", "duongnv2");
//
		PersonalRegisterForm form = new PersonalRegisterForm();
		form.setCustomerName(variables.get("customerName").toString());
		form.setPhoneNumber(variables.get("phoneNumber").toString());

		 variables.put("form", form);

		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("personalRegister", "duongnv2",
				variables);

		System.out.println(processInstance.getId());
		System.out.println(processInstance.getBusinessKey());

		System.out.println(runtimeService.getVariableInstances(processInstance.getId()));
		System.out.println(runtimeService.getVariable(processInstance.getId(), "form"));

	}

//	 @Test
	// 50001
	// 62501
	// 77501
//	 92501
	public void testConfirmOtp() {
		String processInstanceId = "92501";
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee("duongnv2")
				.list();
		assertNotNull(tasks);
		System.out.println(tasks);
		System.out.println(tasks.get(0).getAssignee());

		Map<String, Object> variables = new HashMap<>();
		variables.put("submitOtp", "123456");

		taskService.complete(tasks.get(0).getId(), variables);

		List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).list();
		assertNotNull(instances);

		System.out.println(runtimeService.getVariableInstances(instances.get(0).getId()));
//		System.out.println(runtimeService.getVariableInstancesLocal(instances.get(0).getId()));

	}

	public void testCancelTask() {
		String processInstanceId = "62501";
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee("duongnv2")
				.list();
		assertNotNull(tasks);
		System.out.println(tasks);
		System.out.println(tasks.get(0).getAssignee());
		for (Task task : tasks) {
		}
	}

	// @Test
	public void testService() {
		System.out.println(applicationContext.getBean("customerServiceImpl"));
		System.out.println(applicationContext.getBean("customerService"));
	}

}
