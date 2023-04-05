package com.example.sharecollect.View.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sharecollect.MainActivity;
import com.example.sharecollect.controllers.HttpRequest;
import com.example.sharecollect.controllers.UserController;
import com.example.sharecollect.databinding.FragmentProfileBinding;

/**
 * The type Profile fragment.
 */
public class ProfileFragment extends Fragment {

        private FragmentProfileBinding binding;
        private UserController userController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity mainActivity = getMainActivity();
        userController = UserController.getInstance();
        Log.println(Log.DEBUG, "Profil", userController.getUser().toString());

        final TextView tvPseudo = binding.textViewPseudo;
        final TextView tvEmail = binding.textViewEmail;

        tvPseudo.setText(userController.getUser().getUsername());
        tvEmail.setText(userController.getUser().getEmail());

        makeCircleImage();

        return root;
    }

    private void makeCircleImage(){
        // Récupérer l'image bitmap
        Bitmap bitmap = HttpRequest.getProfilePicture(Integer.toString(userController.getUser().getId()));

        // Si l'image n'existe pas, on ne fait rien et l'image de base est utilisée
        if(bitmap == null){
            return;
        }

        // Créer un shader bitmap avec l'image bitmap
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // Créer un objet Paint pour dessiner la forme du cercle
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        // Définir la forme du cercle
        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);

        // Appliquer l'image ronde à l'ImageView
        ImageView ivAvatar = binding.imageViewProfilePicture;
        ivAvatar.setImageBitmap(output);
    }

    /**
     * Gets main activity.
     *
     * @return the main activity
     */
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}