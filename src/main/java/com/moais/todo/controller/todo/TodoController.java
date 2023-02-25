package com.moais.todo.controller.todo;

import com.moais.todo.common.CommonResponse;
import com.moais.todo.controller.todo.dto.TodoChangeStatusResponse;
import com.moais.todo.controller.todo.dto.TodoResponse;
import com.moais.todo.controller.todo.dto.TodoWriteRequest;
import com.moais.todo.controller.todo.dto.TodoWriteResponse;
import com.moais.todo.domain.Todo;
import com.moais.todo.domain.TodoStatus;
import com.moais.todo.security.AuthorizedMember;
import com.moais.todo.service.todo.TodoService;
import com.moais.todo.service.todo.dto.TodoChangeStatusCommand;
import com.moais.todo.service.todo.dto.TodoChangeStatusResult;
import com.moais.todo.service.todo.dto.TodoWriteCommand;
import com.moais.todo.service.todo.dto.TodoWriteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/latest")
    public CommonResponse<TodoResponse> getLatestOne() {
        Long memberId = authorizedMember.getMemberId();
        Optional<Todo> todoOptional = todoService.findLatestOneByMemberId(memberId);

        if (todoOptional.isPresent()) {
            Todo latestTodo = todoOptional.get();
            return CommonResponse.withBody(new TodoResponse(latestTodo));
        }

        return CommonResponse.emptyBody();
    }

    @GetMapping
    public CommonResponse<List<TodoResponse>> findAll(Pageable pageable) {
        Long memberId = authorizedMember.getMemberId();
        Page<Todo> todos = todoService.findAllByMemberId(memberId, pageable);
        List<TodoResponse> responses = todos.map(TodoResponse::new).getContent();
        return CommonResponse.withPaging(responses, todos);
    }
}
