package hello.john2.domain.item;

import hello.john2.domain.Category;
import hello.john2.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity@Getter@Setter@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")

public abstract class Item {


@Id@GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    //@Transactional
    //public void change(String name ,int price , int stockQuantity){
   //   Book book =new Book();
    //    book.setName(name);
    //    book.setPrice(price);
    //    book.setStockQuantity(stockQuantity);

  //  }

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//

    /**
     *
     * stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     *
     * stock 감소
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity =restStock;
    }
}
