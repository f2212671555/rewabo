const generateId = function _generateId() {
    return (1 + Math.random() * 4294967295).toString(16);
};

const selectOptionsOperator = [
    {v: "eq", t: "=="},
    {v: "neq", t: "!="},
    {v: "lt", t: "<"},
    {v: "lte", t: "<="},
    {v: "gt", t: ">"},
    {v: "gte", t: ">="},
    {v: "btwn", t: "is between"},
    {v: "cont", t: "contains"},
    {v: "regex", t: "match regex"},
    {v: "true", t: "is true"},
    {v: "false", t: "is false"},
    {v: "null", t: "is null"},
    {v: "nnull", t: "is not null"}
];

const gates = {
    and: "and",
    or: "or",
    nand: "nand",
    nor: "nor",
    xor: "xor",
    xnor: "xnor",
};
const dataTypes = {
    num: "num",
    str: "str",
};
const propertyType = {
    msg: "msg",
    flow: "flow",
    global: "global"
};