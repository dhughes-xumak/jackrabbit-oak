/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.oak.query.ast;

import org.apache.jackrabbit.oak.query.Query;

public abstract class AstVisitorBase implements AstVisitor {

    /**
     * Calls accept on each of the attached constraints of the AND node.
     */
    @Override
    public boolean visit(AndImpl node) {
        node.getConstraint1().accept(this);
        node.getConstraint2().accept(this);
        return true;
    }

    /**
     * Calls accept on the two operands in the comparison node.
     */
    @Override
    public boolean visit(ComparisonImpl node) {
        node.getOperand1().accept(this);
        node.getOperand2().accept(this);
        return true;
    }

    /**
     * Calls accept on the static operand in the fulltext search constraint.
     */
    @Override
    public boolean visit(FullTextSearchImpl node) {
        node.getFullTextSearchExpression().accept(this);
        return true;
    }

    /**
     * Calls accept on the two sources and the join condition in the join node.
     */
    @Override
    public boolean visit(JoinImpl node) {
        node.getRight().accept(this);
        node.getLeft().accept(this);
        node.getJoinCondition().accept(this);
        return true;
    }

    /**
     * Calls accept on the property value in the length node.
     */
    @Override
    public boolean visit(LengthImpl node) {
        return node.getPropertyValue().accept(this);
    }

    /**
     * Calls accept on the dynamic operand in the lower-case node.
     */
    @Override
    public boolean visit(LowerCaseImpl node) {
        return node.getOperand().accept(this);
    }

    /**
     * Calls accept on the constraint in the NOT node.
     */
    @Override
    public boolean visit(NotImpl node) {
        return node.getConstraint().accept(this);
    }

    /**
     * Calls accept on the dynamic operand in the ordering node.
     */
    @Override
    public boolean visit(OrderingImpl node) {
        return node.getOperand().accept(this);
    }

    /**
     * Calls accept on each of the attached constraints of the OR node.
     */
    @Override
    public boolean visit(OrImpl node) {
        node.getConstraint1().accept(this);
        node.getConstraint2().accept(this);
        return true;
    }

    /**
     * Calls accept on the following contained QOM nodes:
     * <ul>
     * <li>Source</li>
     * <li>Constraints</li>
     * <li>Orderings</li>
     * <li>Columns</li>
     * </ul>
     */
    public boolean visit(Query node) {
        node.getSource().accept(this);
        ConstraintImpl constraint = node.getConstraint();
        if (constraint != null) {
            constraint.accept(this);
        }
        OrderingImpl[] orderings = node.getOrderings();
        if (orderings != null) {
            for (OrderingImpl ordering : orderings) {
                ordering.accept(this);
            }
        }
        ColumnImpl[] columns = node.getColumns();
        for (ColumnImpl column : columns) {
            column.accept(this);
        }
        return true;
    }

    /**
     * Calls accept on the dynamic operand in the lower-case node.
     */
    @Override
    public boolean visit(UpperCaseImpl node) {
        return node.getOperand().accept(this);
    }

}
