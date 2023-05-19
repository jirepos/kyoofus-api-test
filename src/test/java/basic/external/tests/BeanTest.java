package basic.external.tests;

import org.junit.jupiter.api.Test;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class BeanTest {


    @Getter
    @Setter
    static class MyBean { 
        @Getter(AccessLevel.NONE)
        private boolean isFolder; 
        public boolean isFolder(){ 
            return this.isFolder;
        }
    }

    @Test
    public void testBean(){ 
        MyBean bean = new MyBean(); 
        bean.setFolder(true);
        bean.isFolder();
    }

    
}
