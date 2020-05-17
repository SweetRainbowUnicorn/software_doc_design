import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YahoofinanceTestModule } from '../../../test.module';
import { WorldIndexComponent } from 'app/entities/world-index/world-index.component';
import { WorldIndexService } from 'app/entities/world-index/world-index.service';
import { WorldIndex } from 'app/shared/model/world-index.model';

describe('Component Tests', () => {
  describe('WorldIndex Management Component', () => {
    let comp: WorldIndexComponent;
    let fixture: ComponentFixture<WorldIndexComponent>;
    let service: WorldIndexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [YahoofinanceTestModule],
        declarations: [WorldIndexComponent]
      })
        .overrideTemplate(WorldIndexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorldIndexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorldIndexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorldIndex(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.worldIndices && comp.worldIndices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
