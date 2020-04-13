package com.damgor.foodapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryPageId implements Serializable {
    private Long profileId;
    private Date date;
}
