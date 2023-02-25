package com.moais.todo.controller;

import com.moais.todo.common.CommonResponse;
import com.moais.todo.controller.dto.TodoWriteRequest;
import com.moais.todo.controller.dto.TodoWriteResponse;
import com.moais.todo.security.AuthorizedMember;
import com.moais.todo.service.TodoService;
import com.moais.todo.service.dto.TodoWriteCommand;
import com.moais.todo.service.dto.TodoWriteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/todos")
@RestController
public class TodoController {

    private final AuthorizedMember authorizedMember;

    private final TodoService todoService;

    @PostMapping
    public CommonResponse<TodoWriteResponse> write(@RequestBody @Valid TodoWriteRequest request) {
        Long memberId = authorizedMember.getMemberId();
        TodoWriteCommand command = new TodoWriteCommand(memberId, request.getTitle(), request.getContent());
        TodoWriteResult result = todoService.write(command);
        return CommonResponse.withBody(new TodoWriteResponse(result));
    }

}
