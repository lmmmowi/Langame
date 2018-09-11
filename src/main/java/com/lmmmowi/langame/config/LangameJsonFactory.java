package com.lmmmowi.langame.config;

import com.jfinal.json.IJsonFactory;
import com.jfinal.json.JFinalJson;
import com.jfinal.json.Json;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class LangameJsonFactory implements IJsonFactory {

    @Override
    public Json getJson() {
        return new LangameJson();
    }

    private class LangameJson extends JFinalJson {

        @Override
        protected String toJson(Object value, int depth) {
            if (value != null && depth-- >= 0) {
                if (value instanceof String) {
                    return "\"" + this.escape((String) value) + "\"";
                } else if (value instanceof Double) {
                    return !((Double) value).isInfinite() && !((Double) value).isNaN() ? value.toString() : "null";
                } else if (value instanceof Float) {
                    return !((Float) value).isInfinite() && !((Float) value).isNaN() ? value.toString() : "null";
                } else if (value instanceof Number) {
                    return value.toString();
                } else if (value instanceof Boolean) {
                    return value.toString();
                } else {
                    String result;
                    if (value instanceof Date) {
                        if (value instanceof Timestamp) {
                            // 将原本渲染字符串的实现改为渲染时间戳
                            Long time = ((Timestamp) value).getTime();
                            return time.toString();
                        } else if (value instanceof Time) {
                            return "\"" + value.toString() + "\"";
                        } else {
                            result = this.datePattern != null ? this.datePattern : this.getDefaultDatePattern();
                            return result != null ? "\"" + (new SimpleDateFormat(result)).format(value) + "\"" : "" + ((Date) value).getTime();
                        }
                    } else if (value instanceof Map) {
                        return this.mapToJson((Map) value, depth);
                    } else if (value instanceof Collection) {
                        return this.iteratorToJson(((Collection) value).iterator(), depth);
                    } else {
                        result = this.otherToJson(value, depth);
                        return result != null ? result : "\"" + this.escape(value.toString()) + "\"";
                    }
                }
            } else {
                return "null";
            }
        }
    }
}
