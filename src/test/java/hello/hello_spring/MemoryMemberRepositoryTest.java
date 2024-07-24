package hello.hello_spring;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // findById 반환 타입이 Optional에서 값을 꺼낼때는 get()를 사용한다. 하지만 get()로 바로 꺼내는게 좋은 방법은 아님.
         //  Optional 객체에 get() 메소드를 호출한 경우 NoSuchElementException 이 발생하기 때문에 값을 가져오기 전에 반드시 값이 있는지 확인해야 한다.
         //출처: https://dev-coco.tistory.com/178 [슬기로운 개발생활:티스토리]
        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member memeber1 = new Member();
        memeber1.setName("spring1");
        repository.save(memeber1);


        Member memeber2 = new Member();
        memeber2.setName("spring2");
        repository.save(memeber2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(memeber1);
    }

    @Test
    public void findall(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

    }
}
