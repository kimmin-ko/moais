package com.moais.todo.controller.member;

import com.moais.todo.common.CommonResponse;
import com.moais.todo.controller.member.dto.MemberJoinRequest;
import com.moais.todo.controller.member.dto.MemberJoinResponse;
import com.moais.todo.controller.member.dto.MemberWithdrawalRequest;
import com.moais.todo.controller.member.dto.MemberWithdrawalResponse;
import com.moais.todo.security.AuthorizedMember;
import com.moais.todo.service.member.MemberService;
import com.moais.todo.service.member.dto.MemberJoinResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final AuthorizedMember authorizedMember;

    private final MemberService memberService;

    @PostMapping
    public CommonResponse<MemberJoinResponse> join(@RequestBody @Valid MemberJoinRequest request) {
        MemberJoinResult result = memberService.join(request.toCommand());
        return CommonResponse.withBody(new MemberJoinResponse(result));
    }

    @PostMapping("/withdrawal")
    public CommonResponse<MemberWithdrawalResponse> withdrawal(@RequestBody @Valid MemberWithdrawalRequest request,
                                                               HttpServletResponse httpResponse) {
        boolean result = memberService.withdrawal(authorizedMember.getMemberId(), request.getPassword());
        httpResponse.setHeader("token", "");
        return CommonResponse.withBody(new MemberWithdrawalResponse(result));
    }

}
