package models;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import models.HitModel.Builder;

import org.joda.time.DateTime;
import org.junit.Test;

public class TestHitModel {

    @Test
    public void testEquals() {
        Date date = new Date();
        HitModel hitModel01 = build("id", "test.pdf", date, 12);
        HitModel hitModel02 = build("id", "test.pdf", date, 12);
        
        assertThat(hitModel01).isEqualTo(hitModel02);
        assertThat(hitModel01.toString()).isEqualTo(hitModel02.toString());
    }
    
    @Test
    public void testIsNotEqualsById() {
        Date date = new Date();
        HitModel hitModel01 = build("id-01", "test.pdf", date, 12);
        HitModel hitModel02 = build("id-02", "test.pdf", date, 12);
        
        assertThat(hitModel01).isNotEqualTo(hitModel02);
        assertThat(hitModel01.getId()).isNotEqualTo(hitModel02.getId());
    }
    
    @Test
    public void testIsNotEqualsByFileName() {
        Date date = new Date();
        HitModel hitModel01 = build("id", "test.pdf", date, 12);
        HitModel hitModel02 = build("id", "test.txt", date, 12);
        
        assertThat(hitModel01).isNotEqualTo(hitModel02);
        assertThat(hitModel01.getFileName())
            .isNotEqualTo(hitModel02.getFileName());
    }
    
    @Test
    public void testIsNotEqualsByDate() {
        HitModel hitModel01 = build("id", "test.pdf", new Date(), 12);
        HitModel hitModel02 = build("id", "test.pdf", 
                DateTime.now().plusDays(1).toDate(), 12);
        
        assertThat(hitModel01).isNotEqualTo(hitModel02);
        assertThat(hitModel01.getIndexingDate())
            .isNotEqualTo(hitModel02.getIndexingDate());
    }
    
    @Test
    public void testIsNotEqualsBySize() {
        Date date = new Date();
        HitModel hitModel01 = build("id", "test.pdf", date, 12);
        HitModel hitModel02 = build("id", "test.pdf", date, 14);
        
        assertThat(hitModel01).isNotEqualTo(hitModel02);
        assertThat(hitModel01.getSize()).isNotEqualTo(hitModel02.getSize());
    }
    
    @Test
    public void testEqualsWithADifferentObject() {
        HitModel hitModel01 = build("id", "test.pdf", new Date(), 12);
        
        assertThat(hitModel01).isNotEqualTo("Test with a different object");
    }
    
    private HitModel build(String id, String fileName, Date date, Integer size) {
        Builder builder = new HitModel.Builder();
        builder.withId(id);
        builder.withFileName(Optional.of(fileName));
        builder.withDate(Optional.of(date.toString()));
        builder.withSize(Optional.of(size));
        return builder.build();
    }
    
}
