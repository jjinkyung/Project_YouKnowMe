package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.MemberDto.FindIdRequestDto;
import com.ssafy.uknowme.model.dto.MemberDto.FindIdResponseDto;
import com.ssafy.uknowme.model.dto.MemberDto.MemberInfoResponseDto;
import com.ssafy.uknowme.model.dto.MemberDto.MemberJoinRequestDto;
import com.ssafy.uknowme.model.dto.MemberDto.MemberUpdateDto;
import com.ssafy.uknowme.model.dto.MemberDto.ValidatePasswordRequestDto;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.domain.enums.Role;
import com.ssafy.uknowme.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;


    @Override
    public boolean join(MemberJoinRequestDto dto) {
        if (existsById(dto.getId())) {
            return false;
        } if (existsByNickname(dto.getNickname())) {
            return false;
        } if (existsByTel(dto.getTel())) {
            return false;
        }


        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        Member member = Member.builder()
                .id(dto.getId())
                .password(encoder.encode(dto.getPassword()))
                .name(dto.getName())
                .nickname(dto.getNickname())
                .gender(dto.getGender())
                .birth(dto.getBirth())
                .tel(dto.getTel())
                .smoke(dto.getSmoke())
                .address(dto.getAddress())
                .role(Role.ROLE_USER)
                .build();

        repository.save(member);

        return true;
    }

    @Override
    public boolean update(MemberUpdateDto memberUpdateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 로그인한 회원이 아니면 수정을 허용하지 말아야 한다.
        if (authentication == null) {
            log.info("로그인한 회원이 아닙니다.");
            return false;
        }

        // 본인이 아니면 수정을 허용하지 말아야 한다.
        if (!authentication.getName().equals(memberUpdateDto.getId())) {
            log.info("본인만 정보를 변경할 수 있습니다.");
            return false;
        }

        Member member = repository.findById(memberUpdateDto.getId()).orElseThrow(() -> new IllegalStateException("해당 아이디가 없습니다."));

        member.updateMember(memberUpdateDto);

        return true;
    }

    @Override
    public boolean existsById(String memberId) {
        return repository.existsById(memberId);
    }

    @Override
    public boolean existsByNickname(String memberNickname) {
        return repository.existsByNickname(memberNickname);
    }

    @Override
    public boolean existsByTel(String memberTel) {
        return repository.existsByTel(memberTel);
    }

    @Override
    public boolean delete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 로그인한 회원이 아니면 수정을 허용하지 말아야 한다.
        if (authentication == null) {
            log.info("로그인한 회원이 아닙니다.");
            return false;
        }

        String id = authentication.getName();

        Member member = repository.findById(id).orElseThrow(() -> new IllegalStateException("해당 아이디가 없습니다."));

        member.delete();

        return true;
    }

    @Override
    public MemberInfoResponseDto getMemberInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.info("로그인한 회원이 아닙니다.");
            return null;
        }

        String id = authentication.getName();

        Member member = repository.findById(id).orElseThrow(() -> new IllegalStateException("해당 아이디가 없습니다."));

        MemberInfoResponseDto responseDto = new MemberInfoResponseDto();

        responseDto.convertFromEntity(member);

        return responseDto;
    }

    @Override
    public boolean validatePassword(ValidatePasswordRequestDto dto) {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.info("로그인한 회원이 아닙니다.");
            return false;
        }

        String id = authentication.getName();

        Member member = repository.findById(id).orElseThrow(() -> new IllegalStateException("해당 아이디가 없습니다."));

        if (!encoder.matches(dto.getPassword(), member.getPassword())) {
            log.info("비밀번호가 다릅니다.");
            return false;
        }

        return true;
    }

    @Override
    public FindIdResponseDto findId(FindIdRequestDto requestDto) {

        Optional<Member> optionalMember = repository.findByNameAndTel(requestDto.getName(), requestDto.getTel());

        if (!optionalMember.isPresent()) return null;

        Member member = optionalMember.get();

        FindIdResponseDto responseDto = new FindIdResponseDto();

        responseDto.setId(member.getId());

        return responseDto;
    }

    @Override
    public List<MemberInfoResponseDto> getMemberList() {
        List<Member> members = repository.findAll();
        List<MemberInfoResponseDto> dtoList = new ArrayList<>();

        for (Member member : members) {
            MemberInfoResponseDto dto = new MemberInfoResponseDto();
            dto.convertFromEntity(member);

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public MemberInfoResponseDto getMemberBySeq(int seq) {
        Member member = repository.findById(seq).orElseThrow(IllegalStateException::new);

        MemberInfoResponseDto dto = new MemberInfoResponseDto();
        dto.convertFromEntity(member);

        return dto;
    }
}
