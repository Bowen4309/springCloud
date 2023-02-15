package com.Bibo.common.validator;

import javax.validation.GroupSequence;

/**
 * add group
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {
}
