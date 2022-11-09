package kg.peaksoft.bilingualb6.entites;

import kg.peaksoft.bilingualb6.entites.enums.ContentType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "contents")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Content {
    @Id
    @GeneratedValue(generator = "answer_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "answer_generator", sequenceName = "answer_sequence", allocationSize = 1,initialValue = 4)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @Column(length = 10000)
    private String content;

    public Content(ContentType contentType, String content) {
        this.contentType = contentType;
        this.content = content;
    }
}
