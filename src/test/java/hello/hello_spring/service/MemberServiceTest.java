package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

   // MemberService memberService = new MemberService();
    // new MemoryMemberRepository() 이렇게 객체를 또 만들 필요가 있을까? -> 스프링 부트 특징인 DI 사용
      // 같은 객체를 써야지, 이미 MemoryMemberRepository 클라스에 store가 static으로 되이 있어서(static은 인스턴스와 상관없이 클라스 레벨에 붙는 거) 큰 상관은 없지만
        // 하지만 new MemoryMemberRepository() 또 만든다는 거는 또다른 인스턴스를 만든다는 것이고 -> 뭔가 내용물이 달라지거나 할 수 있따.
        // 지금 현재 MemberService에서 쓰는 new MemoryMemberRepository() 하고 MemberServiceTest에서 사용하는 new MemoryMemberRepository() 하고 다른 인스턴스다.
        // store가 만약에 static이 아니면 바로 문제가 생긴다. 다른 DB가 되버린다. 같은 레포지토리로 테스트를 해야한다!! 그래서 좋은 방법은 memberService 클라스 파일 참고
   // MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach()
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }


    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");
        //when
        Long saveId = memberService.join(member);
        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

/* try catch 보다 더 좋은 방법이 있다. junit 라이브러리에 assertThrows 사용
  try {
            memberService.join(member2);
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
        //then
    }

    @Test
    void findMembers() {
    }
    @Test
    void findOne() {
    }
}