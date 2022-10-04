package uit.javabackend.webclonethecoffeehouse.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import uit.javabackend.webclonethecoffeehouse.common.util.DateTimeUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder // để lớp con kế thừa
@MappedSuperclass // cho các class kế thừa để triển khai xuống DB để tạo bảng
@EntityListeners(AuditingEntityListener.class) // gọi để tự động khởi tạo
public class BaseEntity implements Serializable {
    @Id
    @Type(type = "uuid-char") // lưu xuống DB uuid dạng String
    @GeneratedValue // tự tăng
    @Column(name = Columns.ID) // đặt tên cột cho DB
    protected UUID id;

    @Version // tự động tăng
    @Column(name = Columns.VERSION)
    protected int version ;

    @CreatedBy
    @Column(name = Columns.CREATED_BY)
    protected String createdBy; // người tạo

    @CreatedDate
    @Column(name = Columns.CREATED_AT)
    @DateTimeFormat(pattern = DateTimeUtils.DATETIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATETIME_FORMAT)
    protected LocalDateTime createdAt; // thời gian tạo

    @Column(name = Columns.LAST_MODIFIED_BY)
    @LastModifiedBy
    protected String lastModifiedBy; // người sửa cuối

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATETIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtils.DATETIME_FORMAT)
    @Column(name = Columns.LAST_MODIFIED_AT)
    @LastModifiedDate
    protected LocalDateTime lastModifiedAt; // thời gian sửa cuối

    // inner class
    @UtilityClass // class ko có khởi tọa instance
    static class Columns{
        static final String ID="ID";
        static final String VERSION ="VERSION";
        static final String CREATED_BY ="CREATED_BY";
        static final String CREATED_AT ="CREATED_AT";
        static final String LAST_MODIFIED_BY ="LAST_MODIFIED_BY";
        static final String LAST_MODIFIED_AT ="LAST_MODIFIED_AT";

    }
}
