import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRule, Rule } from 'app/shared/model/rule.model';
import { RuleService } from './rule.service';

@Component({
  selector: 'jhi-rule-update',
  templateUrl: './rule-update.component.html'
})
export class RuleUpdateComponent implements OnInit {
  rule: IRule;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    ruleId: [],
    ruleDescription: [],
    maxContainer: []
  });

  constructor(protected ruleService: RuleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rule }) => {
      this.updateForm(rule);
      this.rule = rule;
    });
  }

  updateForm(rule: IRule) {
    this.editForm.patchValue({
      id: rule.id,
      ruleId: rule.ruleId,
      ruleDescription: rule.ruleDescription,
      maxContainer: rule.maxContainer
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rule = this.createFromForm();
    if (rule.id !== undefined) {
      this.subscribeToSaveResponse(this.ruleService.update(rule));
    } else {
      this.subscribeToSaveResponse(this.ruleService.create(rule));
    }
  }

  private createFromForm(): IRule {
    const entity = {
      ...new Rule(),
      id: this.editForm.get(['id']).value,
      ruleId: this.editForm.get(['ruleId']).value,
      ruleDescription: this.editForm.get(['ruleDescription']).value,
      maxContainer: this.editForm.get(['maxContainer']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRule>>) {
    result.subscribe((res: HttpResponse<IRule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
