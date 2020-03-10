package com.tecsun.robot.bean.sanbao;

import java.util.List;

public class InitialDataDefault {


    /**
     * result : {"bot_id":"aries_general","bot_meta":{"version":"1.0.0","type":"其他","description":"desc"},"nlu":{"domain":"universal_search","intent":"baike","sub_intent":"baike","slots":{"key_word":"毛泽东"}},"speech":{"type":"Text","content":"毛泽东，字润之，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。"},"views":[{"type":"list","list":[{"title":"毛泽东","summary":"毛泽东(1893年12月26日-1976年9月9日)，字润之(原作咏芝，后改润芝)，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。1949至1976年，毛泽东担任中华人民共和国最高领导人。他对马克思列宁主义的发展、军事理论的贡献以及对共产党的理论贡献被称为毛泽东思想。因毛泽东担任过的主要职务几乎全部称为主席，所以也被人们尊称为\u201c毛主席\u201d。毛泽东被视为现代世界历史中最重要的人物之一，《时代》杂志也将他评为20世纪最具影响100人之一。","url":"https://baike.baidu.com/item/%E6%AF%9B%E6%B3%BD%E4%B8%9C/113835","image":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1102072786,2569245295&fm=82"}]}],"hint":["西游记的资料","刘德华百科","琅琊榜的资料","最聪明的十种恐龙","小鸡怎么洗澡","余罪是什么","银杏是什么树","武则天的介绍","金日成简介","鲁迅百科"],"show_hint":[{"cue_words":["西游记的资料","刘德华百科","琅琊榜的资料","最聪明的十种恐龙","小鸡怎么洗澡","余罪是什么","银杏是什么树","武则天的介绍","金日成简介","鲁迅百科"]}],"resources":[],"resource":{"type":"baike","data":{"image":"http://t11.baidu.com/it/u=1102072786,2569245295&fm=82","introduction":"毛泽东，字润之，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。","speech":"毛泽东，字润之，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。","title":"毛泽东","url":"http://baike.baidu.com/item/%E6%AF%9B%E6%B3%BD%E4%B8%9C/113835"},"entity":[{"attributes":{"gender":"unknown"},"id":"","name":"毛泽东","token":"ai.dueros.entity.person.0","type":"ai.dueros.entity.person","version":"1.0"}],"payload":{"token":"eyJib3RfaWQiOiJhcmllc19nZW5lcmFsIiwicmVzdWx0X3Rva2VuIjoiNmM2ODk4MjgxNWU4YThlNWIwNmY0MTJjMDRlMWU0MDkiLCJib3RfdG9rZW4iOiI3NDU1MjMzOC04NGU3LTQwZmQtYTdjMS1jNDRmMDdlYThmNzMiLCJsYXVuY2hfaWRzIjpbImFyaWVzX2dlbmVyYWwiXX0="}}}
     * session_id : a4552338-84e7-40fd-a7c1-c44f07ea8f73
     * id : 1583806796_142475flf
     * logid : a5c97539cfd34bd5b5025476281a6ed4_DCS-10-162-49-153-8280-0310101955-14311403
     * user_id : 6b41ccec4a1e7c691c27a8c2166c274f
     * time : 1583806796
     * cuid : 6b41ccec4a1e7c691c27a8c2166c274f
     * se_query : 毛泽东。
     * msg : ok
     * status : 0
     * client_msg_id : 5315e823-8673-4795-8f59-0a453be8ea65
     */

    private ResultBean result;
    private String session_id;
    private String id;
    private String logid;
    private String user_id;
    private int time;
    private String cuid;
    private String se_query;
    private String msg;
    private int status;
    private String client_msg_id;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getSe_query() {
        return se_query;
    }

    public void setSe_query(String se_query) {
        this.se_query = se_query;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getClient_msg_id() {
        return client_msg_id;
    }

    public void setClient_msg_id(String client_msg_id) {
        this.client_msg_id = client_msg_id;
    }

    public static class ResultBean {
        /**
         * bot_id : aries_general
         * bot_meta : {"version":"1.0.0","type":"其他","description":"desc"}
         * nlu : {"domain":"universal_search","intent":"baike","sub_intent":"baike","slots":{"key_word":"毛泽东"}}
         * speech : {"type":"Text","content":"毛泽东，字润之，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。"}
         * views : [{"type":"list","list":[{"title":"毛泽东","summary":"毛泽东(1893年12月26日-1976年9月9日)，字润之(原作咏芝，后改润芝)，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。1949至1976年，毛泽东担任中华人民共和国最高领导人。他对马克思列宁主义的发展、军事理论的贡献以及对共产党的理论贡献被称为毛泽东思想。因毛泽东担任过的主要职务几乎全部称为主席，所以也被人们尊称为\u201c毛主席\u201d。毛泽东被视为现代世界历史中最重要的人物之一，《时代》杂志也将他评为20世纪最具影响100人之一。","url":"https://baike.baidu.com/item/%E6%AF%9B%E6%B3%BD%E4%B8%9C/113835","image":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1102072786,2569245295&fm=82"}]}]
         * hint : ["西游记的资料","刘德华百科","琅琊榜的资料","最聪明的十种恐龙","小鸡怎么洗澡","余罪是什么","银杏是什么树","武则天的介绍","金日成简介","鲁迅百科"]
         * show_hint : [{"cue_words":["西游记的资料","刘德华百科","琅琊榜的资料","最聪明的十种恐龙","小鸡怎么洗澡","余罪是什么","银杏是什么树","武则天的介绍","金日成简介","鲁迅百科"]}]
         * resources : []
         * resource : {"type":"baike","data":{"image":"http://t11.baidu.com/it/u=1102072786,2569245295&fm=82","introduction":"毛泽东，字润之，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。","speech":"毛泽东，字润之，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。","title":"毛泽东","url":"http://baike.baidu.com/item/%E6%AF%9B%E6%B3%BD%E4%B8%9C/113835"},"entity":[{"attributes":{"gender":"unknown"},"id":"","name":"毛泽东","token":"ai.dueros.entity.person.0","type":"ai.dueros.entity.person","version":"1.0"}],"payload":{"token":"eyJib3RfaWQiOiJhcmllc19nZW5lcmFsIiwicmVzdWx0X3Rva2VuIjoiNmM2ODk4MjgxNWU4YThlNWIwNmY0MTJjMDRlMWU0MDkiLCJib3RfdG9rZW4iOiI3NDU1MjMzOC04NGU3LTQwZmQtYTdjMS1jNDRmMDdlYThmNzMiLCJsYXVuY2hfaWRzIjpbImFyaWVzX2dlbmVyYWwiXX0="}}
         */

        private String bot_id;
        private BotMetaBean bot_meta;
        private NluBean nlu;
        private SpeechBean speech;
        private ResourceBean resource;
        private List<ViewsBean> views;
        private List<String> hint;
        private List<ShowHintBean> show_hint;
        private List<?> resources;

        public String getBot_id() {
            return bot_id;
        }

        public void setBot_id(String bot_id) {
            this.bot_id = bot_id;
        }

        public BotMetaBean getBot_meta() {
            return bot_meta;
        }

        public void setBot_meta(BotMetaBean bot_meta) {
            this.bot_meta = bot_meta;
        }

        public NluBean getNlu() {
            return nlu;
        }

        public void setNlu(NluBean nlu) {
            this.nlu = nlu;
        }

        public SpeechBean getSpeech() {
            return speech;
        }

        public void setSpeech(SpeechBean speech) {
            this.speech = speech;
        }

        public ResourceBean getResource() {
            return resource;
        }

        public void setResource(ResourceBean resource) {
            this.resource = resource;
        }

        public List<ViewsBean> getViews() {
            return views;
        }

        public void setViews(List<ViewsBean> views) {
            this.views = views;
        }

        public List<String> getHint() {
            return hint;
        }

        public void setHint(List<String> hint) {
            this.hint = hint;
        }

        public List<ShowHintBean> getShow_hint() {
            return show_hint;
        }

        public void setShow_hint(List<ShowHintBean> show_hint) {
            this.show_hint = show_hint;
        }

        public List<?> getResources() {
            return resources;
        }

        public void setResources(List<?> resources) {
            this.resources = resources;
        }

        public static class BotMetaBean {
            /**
             * version : 1.0.0
             * type : 其他
             * description : desc
             */

            private String version;
            private String type;
            private String description;

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        public static class NluBean {
            /**
             * domain : universal_search
             * intent : baike
             * sub_intent : baike
             * slots : {"key_word":"毛泽东"}
             */

            private String domain;
            private String intent;
            private String sub_intent;
            private SlotsBean slots;

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

            public String getIntent() {
                return intent;
            }

            public void setIntent(String intent) {
                this.intent = intent;
            }

            public String getSub_intent() {
                return sub_intent;
            }

            public void setSub_intent(String sub_intent) {
                this.sub_intent = sub_intent;
            }

            public SlotsBean getSlots() {
                return slots;
            }

            public void setSlots(SlotsBean slots) {
                this.slots = slots;
            }

            public static class SlotsBean {
                /**
                 * key_word : 毛泽东
                 */

                private String key_word;

                public String getKey_word() {
                    return key_word;
                }

                public void setKey_word(String key_word) {
                    this.key_word = key_word;
                }
            }
        }

        public static class SpeechBean {
            /**
             * type : Text
             * content : 毛泽东，字润之，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。
             */

            private String type;
            private String content;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class ResourceBean {
            /**
             * type : baike
             * data : {"image":"http://t11.baidu.com/it/u=1102072786,2569245295&fm=82","introduction":"毛泽东，字润之，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。","speech":"毛泽东，字润之，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。","title":"毛泽东","url":"http://baike.baidu.com/item/%E6%AF%9B%E6%B3%BD%E4%B8%9C/113835"}
             * entity : [{"attributes":{"gender":"unknown"},"id":"","name":"毛泽东","token":"ai.dueros.entity.person.0","type":"ai.dueros.entity.person","version":"1.0"}]
             * payload : {"token":"eyJib3RfaWQiOiJhcmllc19nZW5lcmFsIiwicmVzdWx0X3Rva2VuIjoiNmM2ODk4MjgxNWU4YThlNWIwNmY0MTJjMDRlMWU0MDkiLCJib3RfdG9rZW4iOiI3NDU1MjMzOC04NGU3LTQwZmQtYTdjMS1jNDRmMDdlYThmNzMiLCJsYXVuY2hfaWRzIjpbImFyaWVzX2dlbmVyYWwiXX0="}
             */

            private String type;
            private DataBean data;
            private PayloadBean payload;
            private List<EntityBean> entity;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public PayloadBean getPayload() {
                return payload;
            }

            public void setPayload(PayloadBean payload) {
                this.payload = payload;
            }

            public List<EntityBean> getEntity() {
                return entity;
            }

            public void setEntity(List<EntityBean> entity) {
                this.entity = entity;
            }

            public static class DataBean {
                /**
                 * image : http://t11.baidu.com/it/u=1102072786,2569245295&fm=82
                 * introduction : 毛泽东，字润之，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。
                 * speech : 毛泽东，字润之，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。
                 * title : 毛泽东
                 * url : http://baike.baidu.com/item/%E6%AF%9B%E6%B3%BD%E4%B8%9C/113835
                 */

                private String image;
                private String introduction;
                private String speech;
                private String title;
                private String url;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getIntroduction() {
                    return introduction;
                }

                public void setIntroduction(String introduction) {
                    this.introduction = introduction;
                }

                public String getSpeech() {
                    return speech;
                }

                public void setSpeech(String speech) {
                    this.speech = speech;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class PayloadBean {
                /**
                 * token : eyJib3RfaWQiOiJhcmllc19nZW5lcmFsIiwicmVzdWx0X3Rva2VuIjoiNmM2ODk4MjgxNWU4YThlNWIwNmY0MTJjMDRlMWU0MDkiLCJib3RfdG9rZW4iOiI3NDU1MjMzOC04NGU3LTQwZmQtYTdjMS1jNDRmMDdlYThmNzMiLCJsYXVuY2hfaWRzIjpbImFyaWVzX2dlbmVyYWwiXX0=
                 */

                private String token;

                public String getToken() {
                    return token;
                }

                public void setToken(String token) {
                    this.token = token;
                }
            }

            public static class EntityBean {
                /**
                 * attributes : {"gender":"unknown"}
                 * id :
                 * name : 毛泽东
                 * token : ai.dueros.entity.person.0
                 * type : ai.dueros.entity.person
                 * version : 1.0
                 */

                private AttributesBean attributes;
                private String id;
                private String name;
                private String token;
                private String type;
                private String version;

                public AttributesBean getAttributes() {
                    return attributes;
                }

                public void setAttributes(AttributesBean attributes) {
                    this.attributes = attributes;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getToken() {
                    return token;
                }

                public void setToken(String token) {
                    this.token = token;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getVersion() {
                    return version;
                }

                public void setVersion(String version) {
                    this.version = version;
                }

                public static class AttributesBean {
                    /**
                     * gender : unknown
                     */

                    private String gender;

                    public String getGender() {
                        return gender;
                    }

                    public void setGender(String gender) {
                        this.gender = gender;
                    }
                }
            }
        }

        public static class ViewsBean {
            /**
             * type : list
             * list : [{"title":"毛泽东","summary":"毛泽东(1893年12月26日-1976年9月9日)，字润之(原作咏芝，后改润芝)，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。1949至1976年，毛泽东担任中华人民共和国最高领导人。他对马克思列宁主义的发展、军事理论的贡献以及对共产党的理论贡献被称为毛泽东思想。因毛泽东担任过的主要职务几乎全部称为主席，所以也被人们尊称为\u201c毛主席\u201d。毛泽东被视为现代世界历史中最重要的人物之一，《时代》杂志也将他评为20世纪最具影响100人之一。","url":"https://baike.baidu.com/item/%E6%AF%9B%E6%B3%BD%E4%B8%9C/113835","image":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1102072786,2569245295&fm=82"}]
             */

            private String type;
            private List<ListBean> list;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * title : 毛泽东
                 * summary : 毛泽东(1893年12月26日-1976年9月9日)，字润之(原作咏芝，后改润芝)，笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，诗人，书法家。1949至1976年，毛泽东担任中华人民共和国最高领导人。他对马克思列宁主义的发展、军事理论的贡献以及对共产党的理论贡献被称为毛泽东思想。因毛泽东担任过的主要职务几乎全部称为主席，所以也被人们尊称为“毛主席”。毛泽东被视为现代世界历史中最重要的人物之一，《时代》杂志也将他评为20世纪最具影响100人之一。
                 * url : https://baike.baidu.com/item/%E6%AF%9B%E6%B3%BD%E4%B8%9C/113835
                 * image : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1102072786,2569245295&fm=82
                 */

                private String title;
                private String summary;
                private String url;
                private String image;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getSummary() {
                    return summary;
                }

                public void setSummary(String summary) {
                    this.summary = summary;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }
            }
        }

        public static class ShowHintBean {
            private List<String> cue_words;

            public List<String> getCue_words() {
                return cue_words;
            }

            public void setCue_words(List<String> cue_words) {
                this.cue_words = cue_words;
            }
        }
    }
}
