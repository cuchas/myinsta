package br.com.cucha.myinsta;

import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.cucha.myinsta.image.ImageInteractor;
import br.com.cucha.myinsta.image.ImageService;
import br.com.cucha.myinsta.image.Photo;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by eduardocucharro on 13/03/17.
 */

public class MainPresenterTest {

    @Test
    public void cameraClick_checksPermission() {

        ImageInteractor interactor = mock(ImageInteractor.class);

        MainView view = mock(MainView.class);

        MainPresenter presenter = new MainPresenterImpl(view, interactor);
        presenter.cameraClick();

        verify(view).checkPermission();
    }

    @Test
    public void cameraClick_showPermissionDialog() {

        ImageInteractor interactor = mock(ImageInteractor.class);

        MainView view = mock(MainView.class);
        when(view.checkPermission()).thenReturn(false);

        MainPresenter presenter = new MainPresenterImpl(view, interactor);
        presenter.cameraClick();

        verify(view).showPermissionDialog();
    }

    @Test
    public void cameraClick_startCamera() {
        ImageInteractor interactor = mock(ImageInteractor.class);

        MainView view = mock(MainView.class);
        when(view.checkPermission()).thenReturn(true);

        MainPresenter presenter = new MainPresenterImpl(view, interactor);
        presenter.cameraClick();

        ArgumentCaptor<File> fileArgumentCaptor = ArgumentCaptor.forClass(File.class);

        verify(view).startCamera(fileArgumentCaptor.capture());
    }

    @Test
    public void init_showEmptyList() {

        ImageInteractor interactor = mock(ImageInteractor.class);

        when(interactor.listAll()).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ImageInteractor mock = (ImageInteractor) invocation.getMock();

                List<Photo> list = new ArrayList<>();
                mock.onListAllSuccess(list);

                return null;
            }
        });

        MainView view = mock(MainView.class);

        MainPresenter presenter = new MainPresenterImpl(view, interactor);

        presenter.init();

        verify(view).showEmptyList();
    }

    @Test
    public void permissionDenied_showNeedPermissionsDialog_whenShouldRationale() {
        ImageInteractor interactor = mock(ImageInteractor.class);
        MainView view = mock(MainView.class);
        when(view.shouldShowDialog()).thenReturn(true);
        MainPresenter presenter = new MainPresenterImpl(view, interactor);

        presenter.permissionDenied();

        verify(view).showPermissionDialog();
    }

    @Test
    public void permissionDenied_notshowNeedPermissionsDialog_whenNotShouldRationale() {
        ImageInteractor interactor = mock(ImageInteractor.class);
        MainView view = mock(MainView.class);
        when(view.shouldShowDialog()).thenReturn(false);
        MainPresenter presenter = new MainPresenterImpl(view, interactor);

        presenter.permissionDenied();

        verify(view, never()).showPermissionDialog();
    }

    @Test
    public void permissionDenied_showUnlockPermissionsDialog_whenNotShouldRationale() {
        ImageInteractor interactor = mock(ImageInteractor.class);
        MainView view = mock(MainView.class);
        when(view.shouldShowDialog()).thenReturn(false);
        MainPresenter presenter = new MainPresenterImpl(view, interactor);

        presenter.permissionDenied();

        verify(view).showUnlockPermissionsDialog();
    }
}
