/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.codehaus.groovy.ast;

import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.classgen.BytecodeExpression;

import java.util.List;

/**
 * Abstract base class for any GroovyCodeVisitor which by default
 * just walks the code and expression tree
 *
 * @author <a href="mailto:james@coredevelopers.net">James Strachan</a>
 */
public abstract class CodeVisitorSupport implements GroovyCodeVisitor {

    public void visitBlockStatement(BlockStatement block) {
        for (Statement statement : block.getStatements()) {
            statement.visit(this);
        }
    }

    public void visitForLoop(ForStatement forLoop) {
        forLoop.getCollectionExpression().visit(this);
        forLoop.getLoopBlock().visit(this);
    }

    public void visitWhileLoop(WhileStatement loop) {
        loop.getBooleanExpression().visit(this);
        loop.getLoopBlock().visit(this);
    }

    public void visitDoWhileLoop(DoWhileStatement loop) {
        loop.getLoopBlock().visit(this);
        loop.getBooleanExpression().visit(this);
    }

    public void visitIfElse(IfStatement ifElse) {
        ifElse.getBooleanExpression().visit(this);
        ifElse.getIfBlock().visit(this);

        Statement elseBlock = ifElse.getElseBlock();
        if (elseBlock instanceof EmptyStatement) {
            // dispatching to EmptyStatement will not call back visitor, 
            // must call our visitEmptyStatement explicitly
            visitEmptyStatement((EmptyStatement) elseBlock);
        } else {
            elseBlock.visit(this);
        }
    }

    public void visitExpressionStatement(ExpressionStatement statement) {
        statement.getExpression().visit(this);
    }

    public void visitReturnStatement(ReturnStatement statement) {
        statement.getExpression().visit(this);
    }

    public void visitAssertStatement(AssertStatement statement) {
        statement.getBooleanExpression().visit(this);
        statement.getMessageExpression().visit(this);
    }

    public void visitTryCatchFinally(TryCatchStatement statement) {
        statement.getTryStatement().visit(this);
        for (CatchStatement catchStatement : statement.getCatchStatements()) {
            catchStatement.visit(this);
        }
        Statement finallyStatement = statement.getFinallyStatement();
        if (finallyStatement instanceof EmptyStatement) {
            // dispatching to EmptyStatement will not call back visitor, 
            // must call our visitEmptyStatement explicitly
            visitEmptyStatement((EmptyStatement) finallyStatement);
        } else {
            finallyStatement.visit(this);
        }
    }

    protected void visitEmptyStatement(EmptyStatement statement) {
        // noop
    }

    public void visitSwitch(SwitchStatement statement) {
        statement.getExpression().visit(this);
        for (CaseStatement caseStatement : statement.getCaseStatements()) {
            caseStatement.visit(this);
        }
        statement.getDefaultStatement().visit(this);
    }

    public void visitCaseStatement(CaseStatement statement) {
        statement.getExpression().visit(this);
        statement.getCode().visit(this);
    }

    public void visitBreakStatement(BreakStatement statement) {
    }

    public void visitContinueStatement(ContinueStatement statement) {
    }

    public void visitSynchronizedStatement(SynchronizedStatement statement) {
        statement.getExpression().visit(this);
        statement.getCode().visit(this);
    }

    public void visitThrowStatement(ThrowStatement statement) {
        statement.getExpression().visit(this);
    }

    public void visitMethodCallExpression(MethodCallExpression call) {
        call.getObjectExpression().visit(this);
        call.getMethod().visit(this);
        call.getArguments().visit(this);
    }

    public void visitStaticMethodCallExpression(StaticMethodCallExpression call) {
        call.getArguments().visit(this);
    }

    public void visitConstructorCallExpression(ConstructorCallExpression call) {
        call.getArguments().visit(this);
    }

    public void visitBinaryExpression(BinaryExpression expression) {
        expression.getLeftExpression().visit(this);
        expression.getRightExpression().visit(this);
    }

    public void visitTernaryExpression(TernaryExpression expression) {
        expression.getBooleanExpression().visit(this);
        expression.getTrueExpression().visit(this);
        expression.getFalseExpression().visit(this);
    }

    public void visitShortTernaryExpression(ElvisOperatorExpression expression) {
        visitTernaryExpression(expression);
    }

    public void visitPostfixExpression(PostfixExpression expression) {
        expression.getExpression().visit(this);
    }

    public void visitPrefixExpression(PrefixExpression expression) {
        expression.getExpression().visit(this);
    }

    public void visitBooleanExpression(BooleanExpression expression) {
        expression.getExpression().visit(this);
    }

    public void visitNotExpression(NotExpression expression) {
        expression.getExpression().visit(this);
    }

    public void visitClosureExpression(ClosureExpression expression) {
        expression.getCode().visit(this);
    }

    public void visitTupleExpression(TupleExpression expression) {
        visitListOfExpressions(expression.getExpressions());
    }

    public void visitListExpression(ListExpression expression) {
        visitListOfExpressions(expression.getExpressions());
    }

    public void visitArrayExpression(ArrayExpression expression) {
        visitListOfExpressions(expression.getExpressions());
        visitListOfExpressions(expression.getSizeExpression());
    }

    public void visitMapExpression(MapExpression expression) {
        visitListOfExpressions(expression.getMapEntryExpressions());

    }

    public void visitMapEntryExpression(MapEntryExpression expression) {
        expression.getKeyExpression().visit(this);
        expression.getValueExpression().visit(this);

    }

    public void visitRangeExpression(RangeExpression expression) {
        expression.getFrom().visit(this);
        expression.getTo().visit(this);
    }

    public void visitSpreadExpression(SpreadExpression expression) {
        expression.getExpression().visit(this);
    }

    public void visitSpreadMapExpression(SpreadMapExpression expression) {
        expression.getExpression().visit(this);
    }

    public void visitMethodPointerExpression(MethodPointerExpression expression) {
        expression.getExpression().visit(this);
        expression.getMethodName().visit(this);
    }

    public void visitUnaryMinusExpression(UnaryMinusExpression expression) {
        expression.getExpression().visit(this);
    }

    public void visitUnaryPlusExpression(UnaryPlusExpression expression) {
        expression.getExpression().visit(this);
    }

    public void visitBitwiseNegationExpression(BitwiseNegationExpression expression) {
        expression.getExpression().visit(this);
    }

    public void visitCastExpression(CastExpression expression) {
        expression.getExpression().visit(this);
    }

    public void visitConstantExpression(ConstantExpression expression) {
    }

    public void visitClassExpression(ClassExpression expression) {
    }

    public void visitVariableExpression(VariableExpression expression) {
    }

    public void visitDeclarationExpression(DeclarationExpression expression) {
        visitBinaryExpression(expression);
    }

    public void visitPropertyExpression(PropertyExpression expression) {
        expression.getObjectExpression().visit(this);
        expression.getProperty().visit(this);
    }

    public void visitAttributeExpression(AttributeExpression expression) {
        expression.getObjectExpression().visit(this);
        expression.getProperty().visit(this);
    }

    public void visitFieldExpression(FieldExpression expression) {
    }

    public void visitGStringExpression(GStringExpression expression) {
        visitListOfExpressions(expression.getStrings());
        visitListOfExpressions(expression.getValues());
    }

    protected void visitListOfExpressions(List<? extends Expression> list) {
        if (list == null) return;
        for (Expression expression : list) {
            if (expression instanceof SpreadExpression) {
                Expression spread = ((SpreadExpression) expression).getExpression();
                spread.visit(this);
            } else {
                expression.visit(this);
            }
        }
    }

    public void visitCatchStatement(CatchStatement statement) {
        statement.getCode().visit(this);
    }

    public void visitArgumentlistExpression(ArgumentListExpression ale) {
        visitTupleExpression(ale);
    }

    public void visitClosureListExpression(ClosureListExpression cle) {
        visitListOfExpressions(cle.getExpressions());
    }

    public void visitBytecodeExpression(BytecodeExpression cle) {
    }
}
