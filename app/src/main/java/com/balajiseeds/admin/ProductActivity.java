package com.balajiseeds.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.balajiseeds.employee.MyCartActivity;
import com.balajiseeds.databinding.ActivityProductBinding;
import com.balajiseeds.employee.models.ModelActivity;
import com.balajiseeds.employee.models.ModelCart;
import com.balajiseeds.employee.models.ModelProducts;
import com.balajiseeds.utils.ImageSliderAdapter;
import com.balajiseeds.utils.WebServices;


import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    ActivityProductBinding binding;
    int qty = 0;
    onQtyChange onQtyChange;
    WebServices webServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices=new WebServices(ProductActivity.this);
        String productId=getIntent().getStringExtra("productid");
        webServices.getProductById(productId, new WebServices.onGetProductById() {
            @Override
            public void getProductById(ModelProducts.Product product) {
                if(product!=null){
                    binding.tvProductName.setText(product.getProductTitle());
                    binding.textView.setText(product.getProductTitle());
                    binding.tvProductDesc.setText(product.getDescription());
                    binding.Variety.setText(product.getProductVariety());
                    ArrayList<String> arrayList = new ArrayList<>();
                    if(product.getImages().isEmpty()){

                    }
                    for(ModelActivity.Photo photo:product.getImages()){
                        arrayList.add(photo.getImageUrl());
                    }
                    ImageSliderAdapter imageSliderAdapter=new ImageSliderAdapter(ProductActivity.this,arrayList);
                    binding.viewPager.setAdapter(imageSliderAdapter);
                    binding.indicator.attachTo(binding.viewPager);

                    binding.textView.setOnClickListener(v -> {
                        getOnBackPressedDispatcher().onBackPressed();
                    });
                    binding.ivCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ProductActivity.this, MyCartActivity.class));
                        }
                    });
                    binding.tvQuantity.setText(""+product.getQty());
                    qty=product.getQty();
                    if (product.getQty() == 0) {
                        binding.llCount.setVisibility(View.GONE);
                        binding.tvAdd.setVisibility(View.VISIBLE);
                       // binding.tvSubmit.setVisibility(View.GONE);


                    } else {
                        binding.llCount.setVisibility(View.VISIBLE);
                        binding.tvAdd.setVisibility(View.GONE);
                       /* binding.tvSubmit.setVisibility(View.VISIBLE);
                        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ProductActivity.this, MyCartActivity.class);
                                startActivity(intent);
                            }
                        });*/
                    }
                    onQtyChange = new onQtyChange() {
                        @Override
                        public void QtyChanged() {
                            if (qty == 0) {
                                webServices.removeFromCart(new ModelCart.deleteFromCart(product.getId(), ""), new WebServices.onResponse() {
                                    @Override
                                    public void response() {
                                        binding.llCount.setVisibility(View.GONE);
                                        binding.tvAdd.setVisibility(View.VISIBLE);
                                    }
                                });

                            } else {
                                webServices.addToCart(new ModelCart.addtoCart(product.getId(), ""+qty), new WebServices.onResponse() {
                                    @Override
                                    public void response() {
                                        binding.llCount.setVisibility(View.VISIBLE);
                                        binding.tvAdd.setVisibility(View.GONE);
                                    }
                                });

                            }
                            binding.tvQuantity.setText("" + qty);
                        }
                    };

                    binding.tvAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            qty++;
                            binding.tvSubmit.setVisibility(View.VISIBLE);
                            binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ProductActivity.this, MyCartActivity.class);
                                    startActivity(intent);
                                }
                            });
                            onQtyChange.QtyChanged();

                        }
                    });
                    binding.btnPlus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            qty++;
                            onQtyChange.QtyChanged();
                        }
                    });
                    binding.btnMinus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (qty > 0) {
                                qty--;
                            }
                            onQtyChange.QtyChanged();
                        }
                    });

                }
            }
        });

        if ( qty == 0){

            binding.tvSubmit.setVisibility(View.GONE);


        }
        else {




           // binding.tvQuantity

        }



//        LinearLayoutManager llm = new LinearLayoutManager(ProductActivity.this);
//        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
//        binding.rvRelatedProducts.setLayoutManager(llm);
//        binding.rvRelatedProducts.setAdapter(new AdapterProducts(ProductActivity.this, false));

    }

    public interface onQtyChange {
        public void QtyChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }
}