package hello.john2.service;

import hello.john2.domain.Member;
import hello.john2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

private final MemberRepository memberRepository;


    //회원가입
    @Transactional
    public  Long join(Member member){
    //중복회원검증
    validateDuplicateMember(member);
        memberRepository.save(member);
return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMember = memberRepository.findByName(member.getName());
    if(!findMember.isEmpty()){
        throw new IllegalStateException("이미존재하는회원입니다.");
    }
    }
    //회원 전체 조회

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);

}
}
