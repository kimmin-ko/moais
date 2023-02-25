package com.moais.todo.controller;

import com.moais.todo.common.CommonResponse;
import com.moais.todo.controller.dto.TodoChangeStatusResponse;
import com.moais.todo.controller.dto.TodoWriteRequest;
import com.moais.todo.controller.dto.TodoWriteResponse;
import com.moais.todo.domain.TodoStatus;
import com.moais.todo.security.AuthorizedMember;
import com.moais.todo.service.TodoChangeStatusCommand;
import com.moais.todo.service.TodoService;
import com.moais.todo.service.dto.TodoChangeStatusResult;
import com.moais.todo.service.dto.TodoWriteCommand;
import com.moais.todo.service.dto.TodoWriteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{id}/status/{status}")
    public CommonResponse<TodoChangeStatusResponse> changeStatus(@PathVariable Long id,
                                                                 @PathVariable TodoStatus status) {
        Long memberId = authorizedMember.getMemberId();
        TodoChangeStatusCommand command = new TodoChangeStatusCommand(memberId, id, status);
        TodoChangeStatusResult result = todoService.changeStatus(command);
        return CommonResponse.withBody(new TodoChangeStatusResponse(result));
    }

}
