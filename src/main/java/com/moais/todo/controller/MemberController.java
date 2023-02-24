package com.moais.todo.controller;

import com.moais.todo.common.CommonResponse;
import com.moais.todo.controller.dto.MemberJoinRequest;
import com.moais.todo.controller.dto.MemberJoinResponse;
import com.moais.todo.service.MemberService;
import com.moais.todo.service.dto.MemberJoinCommand;
import com.moais.todo.service.dto.MemberJoinResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public CommonResponse<MemberJoinResponse> join(@RequestBody @Valid MemberJoinRequest request) {
        MemberJoinResult result = memberService.join(request.toCommand());
        return CommonResponse.withBody(new MemberJoinResponse(result));
    }

}
