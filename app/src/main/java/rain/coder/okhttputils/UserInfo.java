package rain.coder.okhttputils;

import java.io.Serializable;
import java.util.List;

/**
 * Describe :
 * Created by Rain on 17-4-10.
 */
public class UserInfo implements Serializable {

    /**
     * count : 3
     * person : [{"name":"rain","age":23},{"name":"coder","age":22}]
     */

    private int count;
    private List<PersonBean> person;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PersonBean> getPerson() {
        return person;
    }

    public void setPerson(List<PersonBean> person) {
        this.person = person;
    }

    public static class PersonBean {
        /**
         * name : rain
         * age : 23
         */

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "PersonBean{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "count=" + count +
                ", person=" + person +
                '}';
    }
}
