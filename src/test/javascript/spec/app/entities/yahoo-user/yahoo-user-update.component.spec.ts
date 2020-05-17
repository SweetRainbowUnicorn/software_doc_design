import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { YahoofinanceTestModule } from '../../../test.module';
import { YahooUserUpdateComponent } from 'app/entities/yahoo-user/yahoo-user-update.component';
import { YahooUserService } from 'app/entities/yahoo-user/yahoo-user.service';
import { YahooUser } from 'app/shared/model/yahoo-user.model';

describe('Component Tests', () => {
  describe('YahooUser Management Update Component', () => {
    let comp: YahooUserUpdateComponent;
    let fixture: ComponentFixture<YahooUserUpdateComponent>;
    let service: YahooUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YahoofinanceTestModule],
        declarations: [YahooUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(YahooUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(YahooUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(YahooUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new YahooUser(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new YahooUser();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
