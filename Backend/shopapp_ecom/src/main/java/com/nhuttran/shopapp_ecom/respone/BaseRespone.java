package com.nhuttran.shopapp_ecom.respone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseRespone {
    @JsonProperty("created_at")
    private LocalDateTime createAt;
    @JsonProperty("updated_at")
    private LocalDateTime updateAt;
}
