/*
 * This file is generated by jOOQ.
 */
package io.opensw.flypush.api.database.generated.tables.records;


import java.time.Instant;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;

import io.opensw.flypush.api.database.generated.tables.EngineTopic;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TopicRecord extends UpdatableRecordImpl<TopicRecord> implements Record9<Integer, String, String, Boolean, Instant, String, Instant, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>push.topic.id</code>.
     */
    public TopicRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>push.topic.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>push.topic.uuid</code>.
     */
    public TopicRecord setUuid(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>push.topic.uuid</code>.
     */
    public String getUuid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>push.topic.name</code>.
     */
    public TopicRecord setName(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>push.topic.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>push.topic.active</code>.
     */
    public TopicRecord setActive(Boolean value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>push.topic.active</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>push.topic.created_at</code>.
     */
    public TopicRecord setCreatedAt(Instant value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>push.topic.created_at</code>.
     */
    public Instant getCreatedAt() {
        return (Instant) get(4);
    }

    /**
     * Setter for <code>push.topic.created_by</code>.
     */
    public TopicRecord setCreatedBy(String value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>push.topic.created_by</code>.
     */
    public String getCreatedBy() {
        return (String) get(5);
    }

    /**
     * Setter for <code>push.topic.updated_at</code>.
     */
    public TopicRecord setUpdatedAt(Instant value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>push.topic.updated_at</code>.
     */
    public Instant getUpdatedAt() {
        return (Instant) get(6);
    }

    /**
     * Setter for <code>push.topic.updated_by</code>.
     */
    public TopicRecord setUpdatedBy(String value) {
        set(7, value);
        return this;
    }

    /**
     * Getter for <code>push.topic.updated_by</code>.
     */
    public String getUpdatedBy() {
        return (String) get(7);
    }

    /**
     * Setter for <code>push.topic.version</code>.
     */
    public TopicRecord setVersion(Integer value) {
        set(8, value);
        return this;
    }

    /**
     * Getter for <code>push.topic.version</code>.
     */
    public Integer getVersion() {
        return (Integer) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<Integer, String, String, Boolean, Instant, String, Instant, String, Integer> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Integer, String, String, Boolean, Instant, String, Instant, String, Integer> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return EngineTopic.ENGINE_TOPIC.ID;
    }

    @Override
    public Field<String> field2() {
        return EngineTopic.ENGINE_TOPIC.UUID;
    }

    @Override
    public Field<String> field3() {
        return EngineTopic.ENGINE_TOPIC.NAME;
    }

    @Override
    public Field<Boolean> field4() {
        return EngineTopic.ENGINE_TOPIC.ACTIVE;
    }

    @Override
    public Field<Instant> field5() {
        return EngineTopic.ENGINE_TOPIC.CREATED_AT;
    }

    @Override
    public Field<String> field6() {
        return EngineTopic.ENGINE_TOPIC.CREATED_BY;
    }

    @Override
    public Field<Instant> field7() {
        return EngineTopic.ENGINE_TOPIC.UPDATED_AT;
    }

    @Override
    public Field<String> field8() {
        return EngineTopic.ENGINE_TOPIC.UPDATED_BY;
    }

    @Override
    public Field<Integer> field9() {
        return EngineTopic.ENGINE_TOPIC.VERSION;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getUuid();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public Boolean component4() {
        return getActive();
    }

    @Override
    public Instant component5() {
        return getCreatedAt();
    }

    @Override
    public String component6() {
        return getCreatedBy();
    }

    @Override
    public Instant component7() {
        return getUpdatedAt();
    }

    @Override
    public String component8() {
        return getUpdatedBy();
    }

    @Override
    public Integer component9() {
        return getVersion();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getUuid();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public Boolean value4() {
        return getActive();
    }

    @Override
    public Instant value5() {
        return getCreatedAt();
    }

    @Override
    public String value6() {
        return getCreatedBy();
    }

    @Override
    public Instant value7() {
        return getUpdatedAt();
    }

    @Override
    public String value8() {
        return getUpdatedBy();
    }

    @Override
    public Integer value9() {
        return getVersion();
    }

    @Override
    public TopicRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public TopicRecord value2(String value) {
        setUuid(value);
        return this;
    }

    @Override
    public TopicRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public TopicRecord value4(Boolean value) {
        setActive(value);
        return this;
    }

    @Override
    public TopicRecord value5(Instant value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public TopicRecord value6(String value) {
        setCreatedBy(value);
        return this;
    }

    @Override
    public TopicRecord value7(Instant value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    public TopicRecord value8(String value) {
        setUpdatedBy(value);
        return this;
    }

    @Override
    public TopicRecord value9(Integer value) {
        setVersion(value);
        return this;
    }

    @Override
    public TopicRecord values(Integer value1, String value2, String value3, Boolean value4, Instant value5, String value6, Instant value7, String value8, Integer value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TopicRecord
     */
    public TopicRecord() {
        super(EngineTopic.ENGINE_TOPIC);
    }

    /**
     * Create a detached, initialised TopicRecord
     */
    public TopicRecord(Integer id, String uuid, String name, Boolean active, Instant createdAt, String createdBy, Instant updatedAt, String updatedBy, Integer version) {
        super(EngineTopic.ENGINE_TOPIC);

        setId(id);
        setUuid(uuid);
        setName(name);
        setActive(active);
        setCreatedAt(createdAt);
        setCreatedBy(createdBy);
        setUpdatedAt(updatedAt);
        setUpdatedBy(updatedBy);
        setVersion(version);
    }
}
