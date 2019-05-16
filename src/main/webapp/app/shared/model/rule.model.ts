export interface IRule {
  id?: number;
  ruleId?: string;
  ruleDescription?: string;
  maxContainer?: number;
}

export class Rule implements IRule {
  constructor(public id?: number, public ruleId?: string, public ruleDescription?: string, public maxContainer?: number) {}
}
