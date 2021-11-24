package com.example.quizapp
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.TintableCompoundDrawablesView
import com.example.quizapp.Question
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    // TODO (STEP 2: A global variables for current position and questions list.)
    // START
    private var mCurrentPosition: Int = 1 // Default and the first question position
    private var mQuestionsList: ArrayList<Question>? = null
    // END

    // TODO (STEP 5: A global variables for selected option.)
    // START
    private var mSelectedOptionPosition: Int = 0
    // END

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_quiz_questions)

        // TODO (STEP 3: Make the questions list and the current position variable global and remove the logs here.)
        // START
        mQuestionsList = Constants.getQuestions()
        // END

        setQuestion()

        // TODO (STEP 4: Set all the click events for Options using the interface onClick listener)
        // START
        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
        // END
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_option_one -> {

                selectedOptionView(tv_option_one, 1)
            }

            R.id.tv_option_two -> {

                selectedOptionView(tv_option_two, 2)
            }

            R.id.tv_option_three -> {

                selectedOptionView(tv_option_three, 3)
            }

            R.id.tv_option_four -> {

                selectedOptionView(tv_option_four, 4)
            }
            R.id.btn_submit -> {
                if(mSelectedOptionPosition == 0) {
                    mCurrentPosition ++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        } else -> {
                            Toast.makeText(this, "You have successfully completed the Quiz", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if(question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg )

                    if(mCurrentPosition == mQuestionsList!!.size) {
                        btn_submit.text = "FINISH"
                    } else {
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawablesView: Int) {
        when(answer) {
            1 -> tv_option_one.background = ContextCompat.getDrawable(
                this, drawablesView
            )
            2 -> tv_option_two.background = ContextCompat.getDrawable(
                this, drawablesView
            )
            3-> tv_option_three.background = ContextCompat.getDrawable(
                this, drawablesView
            )
            4 -> tv_option_four.background = ContextCompat.getDrawable(
                this, drawablesView
            )
        }
    }

    // TODO (STEP 1 : Lets create a function to set the question in the UI components which we have done earlier the onCreate method. And make some of the variables global which we will be using later.)
    // START
    /**
     * A function for setting the question to UI components.
     */
    private fun setQuestion() {
        val question =
            mQuestionsList!![mCurrentPosition - 1] // Getting the question from the list with the help of current position.

        defaultOptionsView()

        if(mCurrentPosition == mQuestionsList!!.size) {
            btn_submit.text = "FINISH"
        } else {
            btn_submit.text = "SUBMIT"
        }
        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.getMax()

        tv_question.text = question.question
        image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }
    // END

    // TODO (STEP 6: Create a function for view for highlighting the selected option.)
    // START
    /**
     * A function to set the view of selected option view.
     */
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this@QuizQuestionsActivity,
            R.drawable.selected_option_border_bg
        )
    }

    // TODO (STEP 8: Create a function to set default options view.)
    // START
    /**
     * A function to set default options view when the new question is loaded or when the answer is reselected.
     */
    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@QuizQuestionsActivity,
                R.drawable.default_option_border_bg
            )
        }
    }
}