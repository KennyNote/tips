package com.learning.notebook.tips.mvc;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5366683551289698315L;

    private Long userId;
    private String userName;

}
