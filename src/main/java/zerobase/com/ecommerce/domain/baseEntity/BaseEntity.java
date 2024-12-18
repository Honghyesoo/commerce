package zerobase.com.ecommerce.domain.baseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime createAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime updateAt;

    private static final ZoneId KOREA_ZONE_ID = ZoneId.of("Asia/Seoul");
    @PrePersist
    protected void onCreate() {
        this.createAt = ZonedDateTime.now(KOREA_ZONE_ID).toLocalDateTime();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = ZonedDateTime.now(KOREA_ZONE_ID).toLocalDateTime();
    }
}
