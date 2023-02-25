package com.moais.todo.controller;

import com.moais.todo.common.CommonResponse;
import com.moais.todo.controller.dto.MemberJoinRequest;
import com.moais.todo.controller.dto.MemberJoinResponse;
import com.moais.todo.controller.dto.MemberWithdrawalRequest;
import com.moais.todo.controller.dto.MemberWithdrawalResponse;
import com.moais.todo.service.MemberService;
import com.moais.todo.service.dto.MemberJoinResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("/{id}/withdrawal")
    public CommonResponse<MemberWithdrawalResponse> withdrawal(@PathVariable Long id,
                                                               @RequestBody @Valid MemberWithdrawalRequest request,
                                                               HttpServletResponse httpResponse) {
        boolean result = memberService.withdrawal(id, request.getPassword());
        httpResponse.setHeader("token", "");
        httpResponse.setHeader("memberId", "");
        return CommonResponse.withBody(new MemberWithdrawalResponse(result));
    }

}
