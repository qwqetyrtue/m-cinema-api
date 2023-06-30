package cn.hnist.sharo.mcinema.core.model;

public class StatusEnum {
    public enum PostStatus {
        DRAFT("草稿", (short) 0),
        REVIEW("审核", (short) 1),
        RELEASE("发布", (short) 2),
        TRASHED("垃圾站", (short) 3);
        private final String name;
        private final Short value;

        PostStatus(String name, Short value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Short getValue() {
            return value;
        }

        public String k() {
            return name;
        }

        public Short v() {
            return value;
        }
    }

    public enum TicketStatus {
        RESERVE("已预定", (short) 0),
        PAID("已付款", (short) 1),
        USED("已检票", (short) 2),
        REFUND("已退票", (short) 3),
        ACCIDENT("发生错误", (short) -1);

        private final String name;
        private final Short value;

        TicketStatus(String name, Short value) {
            this.name = name;
            this.value = value;
        }

        public Short getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public Short v() {
            return this.value;
        }

        public String k() {
            return this.name;
        }
    }
}
