package hello.john2.service;

import hello.john2.domain.Member;
import hello.john2.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.Assert.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional()
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

   @Rollback(false)
     @Test
public void 회원가입() throws Exception{
//given
Member member = new Member();
member.setName("kim");
//when
         Long savedId = memberService.join(member);

//then
assertEquals(member, memberRepository.findOne(savedId));
         System.out.println("이름은 : "+member.getName());
   em.flush();
     }
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
    //given
    Member member1 = new Member();
    member1.setName("kim");

    Member member2 =new Member();
    member2.setName("kim");
    //when
        memberService.join(member1);
        memberService.join(member2); //예외가발생해야함


    //then
        fail("예외가 발생해야 한다.");
    }
    
}