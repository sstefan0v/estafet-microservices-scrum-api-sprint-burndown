package com.estafet.microservices.api.sprint.burndown.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.sprint.burndown.model.Task;
import com.estafet.microservices.api.sprint.burndown.services.TaskService;

import io.opentracing.Tracer;

@Component
public class UpdateTaskConsumer {
	
	@Autowired
	private Tracer tracer;
	
	@Autowired
	private TaskService taskService;
	
	@JmsListener(destination = "update.task.topic", containerFactory = "myFactory")
	public void onMessage(String message) {
		try {
			taskService.updateTask(Task.fromJSON(message));
		} finally {
			tracer.activeSpan().close();
		}
	}

}
