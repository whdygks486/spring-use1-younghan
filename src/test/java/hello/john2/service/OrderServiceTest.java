package hello.john2.service;

import hello.john2.domain.Address;
import hello.john2.domain.Member;
import hello.john2.domain.Order;
import hello.john2.domain.OrderStatus;
import hello.john2.domain.item.Book;
import hello.john2.domain.item.Item;
import hello.john2.exception.NotEnoughStockException;
import hello.john2.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
@Test
public void 상품주문() throws Exception{
//given
    Member member = createMember();
   Book book = createBook("시골JPA", 10000, 10);
//when
    int orderCount = 2;
   Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
//then
Order getOrder =orderRepository.findOne(orderId);

 assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER,getOrder.getStatus());
assertEquals("주문한 상품 종류 수가 정확해야한다. ",1,getOrder.getOrderItems().size());
assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount,getOrder.getTotalPrice());
assertEquals("주문 수량만큼 재고가 줄어야한다. ",8,book.getStockQuantity());
}

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }

    @Test
public void 주문취소() throws Exception{
//given
        Member member = createMember();
      Book item = createBook("시골JPA",10000,10);
int orderCount =2 ;
Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
      //when
        orderService.cancelOrder(orderId);
//then
Order getOrder = orderRepository.findOne(orderId);
assertEquals("주문 취소시 상태는 CANCEL 이다.",OrderStatus.CANCEL,getOrder.getStatus());
assertEquals("주문이 취소된 상품은 그만큼 재곡 증가해야한다.",10,item.getStockQuantity());
}
@Test(expected = NotEnoughStockException.class)
public void 상품주문_재고수량초과() throws Exception{
//given
Member member =createMember();
Item item = createBook("시골JPA", 10000, 10);
int orderCount = 11;
//when
    orderService.order(member.getId() ,item.getId(),orderCount);

//then
    fail("재고수량 부족 예외가 발생해야한다.");

}
}